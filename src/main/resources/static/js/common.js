layui.config({
    base: _gate + 'plugin/layui/extend/'
})


/**
 * 全局初始化方法
 */
layui.use(['jquery', 'util', 'layer'], function () {
    const {$} = layui

    // 初始化时间
    $('.date-field').each((index, field) => $(field).val(formatDate($(field).val(), $(field).data('format'))))

})


/**
 * 返回格式化后的字符串
 * @param date 时间
 * @param format 格式
 */
function formatDate(date, format = 'yyyy年MM月dd日 HH:mm:ss') {
    return layui.util.toDateString(date, format)
}


/**
 * 整理tree为layuitree数据格式
 */
function formatTree({tree, parseNode, depth = 0, spreadDepth = 0}) {
    for (let node of tree) {
        parseNode && parseNode(node)
        if (node.children) {
            node.spread = depth <= spreadDepth && echos.some(({id}) => id === node.id)
            formatTree({
                tree: node.children,
                parseNode,
                depth: ++depth,
                spreadDepth
            })
        } else {
            // 只选择最底层,避免父节点被选中子节点则全部选中
            node.checked = echos.some(({id}) => id === node.id)
        }
    }
    return tree
}

/**
 * 创建 iframe 窗口，手机端自动全屏
 * @param title 窗口标题
 * @param url 地址
 * @param area 窗口尺寸
 */
function newFrame(title, url, area) {
    let index = layer.open({
        type: 2,
        title: title,
        area: area ? area : ['700px', '620px'],
        fixed: false,
        content: url
    })
    window.innerWidth < 768 && layer.full(index)
    return false
}

/**
 * 校验操作是否失败
 * @param code 返回码
 */
function fail(code) {
    return '0000' !== code
}

/**
 * 全局http请求
 */
function httpPost({url, data, cb, load = true}) {
    load && loading()
    layui.jquery.post(url, data, ({code, info, data}) => {
        load && loaded()
        if (fail(code)) return warn(info)
        cb(data)
    })
    return false
}

/**
 * 将数组合并到data中
 */
function merge({arr, name, data}) {
    if (!arr || !arr.length) return
    for (let i = 0; i < arr.length; i++) {
        data[`${name}[${i}]`] = arr[i]
    }
}


/**
 * 文本复制
 * @param text 要复制的文本
 */
function copy(text) {
    const input = document.createElement('input')
    input.value = text
    document.body.appendChild(input)
    if (navigator.userAgent.match(/ipad|iphone/i)) {
        // 创建一个文档的连续范围区域，如用户在浏览器窗口中用鼠标拖动选中的区域
        let range = document.createRange()
        range.selectNodeContents(input)
        let selection = window.getSelection()
        selection.removeAllRanges()
        selection.addRange(range)
        input.setSelectionRange(0, 999999);  //选择范围，确保全选
    } else {
        input.select()
    }
    try {
        document.execCommand("Copy", false, null) ? msg(null, '复制成功') : warn('复制失败')
    } catch (e) {
        warn('复制失败')
    }
    document.body.removeChild(input)
}


//---------------------------------------------- 通知类 ----------------------------------------------

/**
 * 显示加载蒙层
 */
let loaderIndex

function loading() {
    loaderIndex = layui.layer.load(2)
}

/**
 * 关闭加载蒙层
 */
function loaded() {
    layui.layer.close(loaderIndex)
}

/**
 * 显示警告
 * @param msg 警告内容
 */
function warn(msg) {
    layui.layer.msg(msg, {icon: 7})
    return false
}


/**
 * 显示成功通知
 *
 * @param cb 回调函数 默认刷新当前表单
 * @param msg 通知的消息
 */
function msg(cb, msg = '操作成功') {
    layui.layer.msg(msg, {icon: 1, time: 500}, cb)
}


/**
 * 显示父级成功通知。当前窗口关闭，提醒依然存在
 *
 * @param cb 回调函数 默认刷新当前表单
 * @param msg 通知的消息
 */
function outMsg(cb, msg = '操作成功') {
    parent.layer.msg('操作成功', {icon: 1, time: 500}, cb);
}

/**
 * 悬浮提示
 * @param id 元素id
 * @param msg 提示信息
 */
function tips(id, msg) {
    parent.layer.tips(msg, `#${id}`, {
        tips: [2, "#1E9FFF"],
        time: 1500
    })
}