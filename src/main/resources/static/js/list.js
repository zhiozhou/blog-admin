layui.use(['table','form','jquery'], ()=> {
    const table = layui.table,
        form = layui.form,
        $ = layui.jquery


    // 添加页面
    $('.add-btn').click(()=> {
        newFrame(`添加${m.name}`, `${prefix}/add`)
    })

    // 添加页面 - 直接打开
    $('.add-go-btn').click(()=> {
        window.location.href = `${prefix}/add/`
    })

    // 列表筛选
    form.on('submit(search)', ({field: data})=> {
        table.reload('table', {
            where: data,
            page: {
                curr: 1
            }
        })
        return false
    })

    // 列表筛选无分页
    form.on('submit(search-all)', ({field: data})=> {
        table.reload('table', {
            where: data
        })
        return false
    })

    // 筛选重置
    $('.search-reset').click(()=>{
        table.reload('table', {
            where:null,
            page: { curr: 1}
        })
    })

    // 筛选重置无分页
    $('.search-reset-all').click(()=>{
        table.reload('table', {
            where:null
        })
    })

})

/**
 * 默认解析
 */
function defaultParse({code, info:msg, data}){
    return {
        code,
        msg,
        count:data.count,
        data:data.list
    }
}

/**
 * 默认填充table参数
 */
function fillDefault (cols , page = true, parseData = defaultParse){
    return {
        id: 'table',
        elem: '#table',
        page: true,
        method: 'post',
        url:  `${prefix}/rest/list`,
        cols: cols,
        parseData: parseData
    }
}


/**
 * 模板控制方法
 * 需要定义模块名name
 * 以iframe方式打开
 */
function handleAction({data, event: action}) {
    switch (action) {
        case 'detail':
            newFrame(`${m.name}详情`, `${prefix}/detail/${data.id}`);
            break
        case 'update':
            newFrame(`修改${m.name}` , `${prefix}/update/${data.id}`);
            break
        case 'remove':
            return removeAction(data)
    }
}

/**
 * 以页面方式打开
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
function removeAction(data){
    layer.confirm(`确认移除该${m.name}吗`, {
        btn: ['确定', '取消'],
        shade: [0.1, '#fff']
    }, (index) => {
        post(`${prefix}/rest/remove/${data.id}`,null,()=>{
            done()
        })
    });
}