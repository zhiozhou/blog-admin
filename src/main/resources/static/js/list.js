layui.use(['table', 'form', 'jquery'], () => {
    const table = layui.table,
        form = layui.form,
        $ = layui.jquery


    /**
     * 使用iframe方式打开添加页面
     */
    $('.add-btn').click(() => {
        newFrame(`添加${m.name}`, `${prefix}/add`)
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
    form.on('submit(search)', ({field: data}) => {
        table.reload('table', {
            where: data,
            page: {
                curr: 1
            }
        })
        return false
    })

    /**
     * 表格筛选无分页
     */
    form.on('submit(search-all)', ({field: data}) => {
        table.reload('table', {
            where: data
        })
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
 * 通用解析列表的响应格式
 */
function defaultParse({code, info: msg, data}) {
    return {
        code,
        msg,
        count: data.count,
        data: data.list
    }
}

/**
 * 表格的通用配置
 *
 * @param cols 自定义列信息
 * @param page 是否开启分页
 * @param parseData 自定义解析格式
 */
function fillDefault(cols, page = true, parseData = defaultParse) {
    return {
        id: 'table',

        elem: '#table',
        page: true,
        cellMinWidth: '80',
        method: 'post',
        url: `${prefix}/rest/list`,
        cols: cols,
        parseData: parseData
    }
}


/**
 * 通用表格的工具栏（删 改 查），全部以iframe窗口方式打开
 */
function handleAction({data, event: action}) {
    switch (action) {
        case 'detail':
            newFrame(`${m.name}详情`, `${prefix}/detail/${data.id}`)
            break
        case 'update':
            newFrame(`修改${m.name}`, `${prefix}/update/${data.id}`)
            break
        case 'remove':
            return removeAction(data)
    }
}

/**
 * 通用表格的工具栏（删 改 查），全部新页面的方式打开
 */
function handleAction2({data, event: action}) {
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
    layer.confirm(`确认移除该${m.name}吗`, {
        btn: ['确定', '取消'],
        shade: [0.1, '#fff']
    }, (index) => {
        post(`${prefix}/rest/remove/${data.id}`, null, () => {
            done(reloadTable)
        })
    })
}

/**
 * 刷新表格
 */
function reloadTable() {
    layui.table.reload('table', {
        where: null,
        page: {curr: 1}
    })
}




