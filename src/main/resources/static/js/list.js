layui.use(['table', 'form', 'jquery'], () => {
    const {table, form, jquery: $} = layui

    /**
     * 使用iframe方式打开添加页面
     */
    $('.add-btn').click(() => {
        newFrame(`添加${_module.name}`, `${prefix}/add`)
    })

    /**
     * 使用新的页面打开添加页
     */
    $('.add-go-btn').click(() => {
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


/**
 * 通用表格的工具栏（删 改 查），全部以iframe窗口方式打开
 */
function iframeAction({data, event: action}) {
    switch (action) {
        case 'detail':
            newFrame(`${_module.name}详情`, `${prefix}/detail/${data.id}`)
            break
        case 'update':
            newFrame(`修改${_module.name}`, `${prefix}/update/${data.id}`)
            break
        case 'remove':
            return removeAction(data)
    }
}

/**
 * 通用表格的工具栏（删 改 查），全部新页面的方式打开
 */
function pageAction({data, event: action}) {
    switch (action) {
        case 'detail':
            window.location.href = `${prefix}/detail/${data.id}`
            break
        case 'update':
            window.location.href = `${prefix}/update/${data.id}`
            break
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
            cb: () => done(reloadTable)
        })
    })
}

/**
 * 刷新表格
 */
function reloadTable() {
    layui.table.reload('table')
}




