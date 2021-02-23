layui.use(['table', 'form', 'jquery'], () => {
    const {table, form, jquery: $} = layui

    /**
     * 使用iframe方式打开添加页面
     */
    $('.add-iframe-btn').click(() => {
        newFrame(`添加${_module.name}`, `${prefix}/add`)
    })

    /**
     * 使用新的页面打开添加页
     */
    $('.add-page-btn').click(() => {
        window.location.href = `${prefix}/add`
    })

    /**
     * 表格筛选
     */
    form.on('submit(search)', ({field: where}) => {
        table.reload('table', {
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
        table.reload('table', {where})
        return false
    })

    /**
     * 表格重置
     */
    $('.search-reset').click(() => {
        table.reload('table', {
            where: null,
            page: {curr: 1}
        })
    })

    /**
     * 表格重置无分页
     */
    $('.search-reset-all').click(() => {
        table.reload('table', {
            where: null
        })
    })

})


/**
 * 表格渲染通用配置
 */
function tableRender(table, options) {
    table.render({
        ...{
            id: 'table',
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
}


const treeTableOptions = {
    parseData: null,
    template: null,
    done: null
}

/**
 * 初始化树形表格
 */
function initTreeTable() {
    const $ = layui.$

    // 子级列表 显示/隐藏
    $('body').on('click', '.tree-btn', function childHandle() {
        let $btn = $(this),
            id = $btn.data('id'),
            childs = $(`[data-parent-id=${id}]`),
            show = 'fa-chevron-down',
            hide = 'fa-chevron-right'

        if ($btn.hasClass(hide)) {

            console.log('if')
            // 展开
            $btn.removeClass(hide).addClass(show)
            childs.parents('tr').show()
        } else {

            console.log('else')
            // 收起
            $btn.removeClass(show).addClass(hide)
            childs.each((index, child) => {
                // 子级为目录，孙级也隐藏
                if ($(child).is(`.tree-btn.${show}`)) {
                    childHandle(child)
                }
                console.log(child)
                $(child).parents('tr').hide()
            })
        }
    })

    treeTableOptions.parseData = ({code, info: msg, data: src}) => {
        let dest = null
        if (src) {
            const term = (dest, src, depth) => {
                for (let item of src) {
                    item.depth = depth
                    let {id, children} = item
                    if (children) {
                        // 将子元素插入父级元素后
                        dest.splice(1 + parseInt(dest.findIndex(({itemId}) => id === itemId)), 0, ...children)
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
    }

    treeTableOptions.template = ({id, depth, parentId, children, name}) => {
        let div = $(`<i><span>${name}</span></i>`)
            .addClass(`depth-${depth}`)
            .attr({'data-id': id, 'data-parent-id': parentId})
        children && div.addClass('tree-btn fa fa-chevron-right')
        return div.prop('outerHTML')
    }

    treeTableOptions.done = () => {
        let parentId = $(`[data-id=${openId}]`).data('parent-id')
        $('[data-parent-id]')
            .not(`[data-parent-id=${openId}],[data-parent-id=${parentId}],[data-parent-id=0]`)
            .parents('tr').hide()
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


/**
 * 通用表格的工具栏（删 改 查），全部以iframe窗口方式打开
 */
function iframeAction({data, event}) {
    switch (event) {
        case 'detail':
            return newFrame(`${_module.name}详情`, `${prefix}/detail/${data.id}`)
        case 'update':
            return newFrame(`修改${_module.name}`, `${prefix}/update/${data.id}`)
        case 'remove':
            return removeAction(data)
    }
}

/**
 * 通用表格的工具栏（删 改 查），全部新页面的方式打开
 */
function pageAction({data, event}) {
    switch (event) {
        case 'detail':
            window.location.href = `${prefix}/detail/${data.id}`
            return
        case 'update':
            window.location.href = `${prefix}/update/${data.id}`
            return
        case 'remove':
            return removeAction(data)
    }
}

/**
 * 通用删除操作
 */
function removeAction(data) {
    layer.confirm(`确认移除该${_module.name}吗`, {
        btn: ['确定', '取消'],
        shade: [0.1, '#fff']
    }, (index) => {
        httpPost({
            url: `${prefix}/rest/remove/${data.id}`,
            cb: () => msg(reloadTable)
        })
    })
}

/**
 * 刷新表格
 */
function reloadTable() {
    layui.table.reload('table')
}




