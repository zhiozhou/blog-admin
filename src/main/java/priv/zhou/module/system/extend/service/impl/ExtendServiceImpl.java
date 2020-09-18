package priv.zhou.module.system.extend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.service.IDictService;
import priv.zhou.module.system.extend.domain.Demo;
import priv.zhou.module.system.extend.domain.dao.ColumnDAO;
import priv.zhou.module.system.extend.domain.dao.TableDAO;
import priv.zhou.module.system.extend.domain.dto.AppConfig;
import priv.zhou.module.system.extend.domain.dto.ColumnDTO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;
import priv.zhou.module.system.extend.domain.po.ColumnPO;
import priv.zhou.module.system.extend.domain.po.TablePO;
import priv.zhou.module.system.extend.service.IExtendService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.Objects.isNull;
import static priv.zhou.common.param.AppProperties.ENC;
import static priv.zhou.common.param.CONSTANT.DEMO_PATH;
import static priv.zhou.common.param.CONSTANT.SEPARATOR;

@Slf4j
@Service
public class ExtendServiceImpl implements IExtendService {

    private final TableDAO tableDAO;

    private final ColumnDAO columnDAO;

    private final IDictService dictService;

    public ExtendServiceImpl(TableDAO tableDAO, ColumnDAO columnDAO, IDictService dictService) {
        this.tableDAO = tableDAO;
        this.columnDAO = columnDAO;
        this.dictService = dictService;
    }

    @Override
    public OutVO<byte[]> module(List<String> tableNames) throws Exception {

        if (null == tableNames || tableNames.isEmpty()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        Map<String, DictDataDTO> configMap = dictService.dataMap(new DictDTO().setKey("extend_config"), false).getData();
        AppConfig appConfig = new AppConfig()
                .setPacket(configMap.get("packet").getLabel())
                .setAuthor(configMap.get("author").getLabel())
                .setModule(configMap.get("module").getLabel())
                .setKeepPrefix(configMap.get("keepPrefix").getLabel().startsWith("t"));

        List<TablePO> poList = tableDAO.list(new TableDTO().setNameIn(tableNames));
        return coreGenerate(appConfig, DTO.ofPO(poList, TableDTO::new));
    }

    /**
     * 初始化模板，生成对应代码
     */
    private OutVO<byte[]> coreGenerate(AppConfig appConfig, List<TableDTO> tableList) throws Exception {

        // 初始化
        VelocityContext context = new VelocityContext();
        context.put("app", initDemo(appConfig));

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ZipOutputStream zipStream = new ZipOutputStream(outStream);

        // 循环渲染
        for (TableDTO table : tableList) {

            // 初始化表
            List<ColumnPO> poList = columnDAO.list(new ColumnDTO().setTableName(table.getName()));
            List<ColumnDTO> dtoList = DTO.ofPO(poList, ColumnDTO::new);
            table.setColumnList(dtoList);
            if (!appConfig.getKeepPrefix()) {
                table.setClassName(table.getName().substring(table.getName().indexOf("_") + 1));
            }

            context.put("table", table);
            for (Demo demo : appConfig.getDemoList()) {

                // 渲染模板
                StringWriter buffer = new StringWriter();
                Template template = Velocity.getTemplate(DEMO_PATH + demo.getPath(), ENC);
                template.merge(context, buffer);

                // 输出到zip
                zipStream.putNextEntry(new ZipEntry(demo.getOutPath(appConfig.getModule(), table.getClassName(), table.getObjectName())));
                IOUtils.write(buffer.toString(), zipStream, ENC);
                zipStream.closeEntry();
            }
        }
        zipStream.close();
        return OutVO.success(outStream.toByteArray());
    }


    /**
     * 初始化Demo
     */
    private AppConfig initDemo(AppConfig appConfig) {
        appConfig.setDemoList(Lists.newArrayList());
        for (File file : nullFill(new File(DEMO_PATH).listFiles())) {
            putDemo(appConfig, file, "");
        }
        return appConfig;
    }

    /**
     * 递归放入所有模板资源
     */
    private void putDemo(AppConfig appConfig, File file, String path) {
        path += SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File temp : nullFill(file.listFiles())) {
                putDemo(appConfig, temp, path);
            }
            return;
        }
        appConfig.getDemoList().add(new Demo(path));
    }

    /**
     * 缺省空数组
     */
    private File[] nullFill(File[] files) {
        return isNull(files) ? new File[]{} : files;
    }


    @SuppressWarnings("all")
    private File createFile(String path) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }
}