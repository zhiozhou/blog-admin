package priv.zhou.module.system.extend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.Result;
import priv.zhou.module.system.dict.domain.vo.DictDataVO;
import priv.zhou.module.system.dict.service.IDictService;
import priv.zhou.module.system.extend.domain.bo.Demo;
import priv.zhou.module.system.extend.domain.bo.TableBO;
import priv.zhou.module.system.extend.domain.dao.TableDAO;
import priv.zhou.module.system.extend.domain.dto.AppConfig;
import priv.zhou.module.system.extend.domain.query.TableQuery;
import priv.zhou.module.system.extend.service.IExtendService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static priv.zhou.common.constant.GlobalConst.DEFAULT_ENC;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExtendServiceImpl implements IExtendService {


    private final TableDAO tableDAO;


    private final IDictService dictService;

    @Override
    public Result<byte[]> generate(List<String> names) throws Exception {

        Map<String, DictDataVO> configMap = dictService.mapDataVO(CONFIG_KEY);
        AppConfig appConfig = new AppConfig()
                .setPacket(configMap.get("packet").getLabel())
                .setAuthor(configMap.get("author").getLabel())
                .setModule(configMap.get("module").getLabel())
                .setKeepPrefix(configMap.get("keepPrefix").getLabel().startsWith("t"));

        // todo 使用bo子查询列 参考kid-server
        List<TableBO> tables = tableDAO.listBO(new TableQuery().setInNames(names));
        return coreGenerate(appConfig, tables);
    }

    /**
     * 初始化模板，生成对应代码
     */
    private Result<byte[]> coreGenerate(AppConfig appConfig, List<TableBO> tables) throws Exception {

        // 初始化
        VelocityContext context = new VelocityContext();
        context.put("app", initDemo(appConfig));

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ZipOutputStream zipStream = new ZipOutputStream(outStream);

        // 循环渲染
        for (TableBO table : tables) {

            if (!appConfig.getKeepPrefix()) {
                table.setClassName(table.getName().substring(table.getName().indexOf("_") + 1));
            }

            context.put("table", table);
            for (Demo demo : appConfig.getDemoList()) {

                // 渲染模板
                StringWriter buffer = new StringWriter();
                Template template = Velocity.getTemplate(DEMO_PATH + demo.getPath(), DEFAULT_ENC);
                template.merge(context, buffer);

                // 输出到zip
                zipStream.putNextEntry(new ZipEntry(demo.getOutPath(appConfig.getModulePath(), table.getClassName(), table.getObjectName())));
                IOUtils.write(buffer.toString(), zipStream, DEFAULT_ENC);
                zipStream.closeEntry();
            }
        }
        zipStream.close();
        return Result.success(outStream.toByteArray());
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
        path += "/" + file.getName();
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
        return null == files ? new File[]{} : files;
    }


    @SuppressWarnings("all")
    private File createFile(String path) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }
}