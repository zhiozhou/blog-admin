const tableId = 'table'
layui.use(['table', 'form', 'jquery'], () => {
    const {table, form, $} = layui

    /**
     * 使用iframe方式打开添加页面
     */
    $('.add-iframe-btn').click(() => {
        newFrame(`添加${_module.name}`, `${prefix}/add`)
    })

    /**
     * 使用新的页面打开添加页
     */
    $('.add-page-btn').click(() => goto(`${prefix}/add`))

    /**
     * 表格筛选
     */
    form.on('submit(search)', ({field: where}) => {
        table.reload(tableId, {
            where,
            page: {
                curr: 1
            }
        })
        return false
    })

    /**
     * 表格筛选无分页
     */
    form.on('submit(search-all)', ({field: where}) => {
        table.reload(tableId, {where})
        return false
    })

    /**
     * 表格重置
     */
    $('.search-reset').click(() => {
        table.reload(tableId, {
            where: null,
            page: {curr: 1}
        })
    })

    /**
     * 表格重置无分页
     */
    $('.search-reset-all').click(() => {
        table.reload(tableId, {
            where: null
        })
    })

})

/**
 * 表格渲染通用配置
 * @param table layui.table组件
 * @param options 表格渲染参数，会替换默认参数
 * @param idField 唯一标识name
 * @param onTool 工具栏监听
 * @param iframe 工具栏监听是否使用iframe
 */
function tableRender({table, options, idField = 'id', onTool, iframe = true}) {
    table.render({
        ...{
            id: tableId,
            elem: '#table',
            page: true,
            cellMinWidth: '80',
            method: 'post',
            url: `${prefix}/rest/list`,
            parseData: ({code, info: msg, data}) => {
                return {
                    code,
                    msg,
                    count: data.count,
                    data: data.list
                }
            }
        },
        ...options
    })

    const toolMap = {
        ...iframe ?
            {
                detail: (data) => newFrame(`${_module.name}详情`, `${prefix}/detail/${data[idField]}`),
                update: (data) => newFrame(`修改${_module.name}`, `${prefix}/update/${data[idField]}`),
                remove
            } :
            {
                detail: (data) => goto(`${prefix}/detail/${data[idField]}`),
                update: (data) => goto(`${prefix}/update/${data[idField]}`),
                remove
            },
        ...onTool
    }

    options.cols[0].some(col => col.toolbar) && table.on('tool(table)', ({event, data}) => {
        const action = toolMap[event]
        action && action(data)
    })


    function remove(data) {
        confirm({
            msg: `确认移除该${_module.name}吗`,
            cb: () => {
                const param = {}
                param[`${idField}s`] = data[idField]
                httpPost({
                    url: `${prefix}/rest/remove`,
                    data: param,
                    cb: ({info}) => msg(reloadTable, info)
                })
            }
        })
    }
}


/**
 * 初始化树形表格
 */
function initTreeTable() {
    const $ = layui.$

    // 子级列表 显示/隐藏
    $('body').on('click', '.tree-btn', function () {
        childHandle(this)
    })

    function childHandle(btn) {
        let $btn = $(btn),
            id = $btn.data('id'),
            childs = $(`[data-parent-id=${id}]`),
            show = 'fa-chevron-down',
            hide = 'fa-chevron-right'

        if ($btn.hasClass(hide)) {
            // 展开
            $btn.removeClass(hide).addClass(show)
            childs.parents('tr').show()
        } else {
            // 收起
            $btn.removeClass(show).addClass(hide)
            childs.each((index, child) => {
                // 子级为目录，孙级也隐藏
                if ($(child).is(`.tree-btn.${show}`)) {
                    childHandle(child)
                }
                $(child).parents('tr').hide()
            })
        }
    }

    return {
        parseData: ({code, info: msg, data: src}) => {
            let dest = null
            if (src) {
                const term = (dest, src, depth) => {
                    for (let item of src) {
                        item.depth = depth
                        let {id, children} = item
                        if (children) {
                            // 将子元素插入父级元素后
                            dest.splice(1 + parseInt(dest.findIndex(({id: itemId}) => id === itemId)), 0, ...children)
                            // 子孙级递归插入
                            term(dest, children, 1 + depth)
                            --item.depth
                        }
                    }
                }
                dest = [...src]
                term(dest, src, 0)
            }
            return {code, msg, "data": dest}
        },
        done: () => {
            let parentId = $(`[data-id=${openId}]`).data('parent-id')
            $('[data-parent-id]')
                .not(`[data-parent-id=${openId}],[data-parent-id=${parentId}],[data-parent-id=0]`)
                .parents('tr').hide()
        },
        template: ({id, depth, parentId, children, name}) => {
            let div = $(`<i><span>${name}</span></i>`)
                .addClass(`depth-${depth}`)
                .attr({'data-id': id, 'data-parent-id': parentId})
            children && div.addClass('tree-btn fa fa-chevron-right')
            return div.prop('outerHTML')
        }
    }
}

/**
 * 通用的字典字段渲染
 * @param code 字段值
 * @param dictMap 字典Map
 * @returns {string} html标签
 */
function dictRender(code, dictMap) {
    let {label, spare} = dictMap[code]
    return `<a class="layui-btn layui-btn-xs ${spare}">${label}</a>`
}




