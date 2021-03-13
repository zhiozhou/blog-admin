layui.config({
    base: _gate + 'plugin/layui/extend/'
})


/**
 * 全局初始化方法
 */
layui.use(['layer'], () => {
    // 预留方法
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
 * 整理data为layuitree数据格式
 * @param data 树形结构数据，结构为 {id,parentId,name,children},可以通过parseNode方法解析
 * @param parseNode 解析节点方法
 * @param handleTree 处理子树方法
 * @param handleLeaf 处理叶子节点方法
 *
 */
function formatTree({data, parseNode, handleTree, handleLeaf}) {
    format(data, 0)

    function format(tree, depth) {
        for (let node of tree) {
            parseNode && parseNode(node, depth)
            if (!node.children) {
                handleLeaf && handleLeaf(node, depth)
                continue
            }
            handleTree && handleTree(node, depth)
            format(node.children, ++depth)
        }
    }

    return data
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

//---------------------------------------------- 锁操作 ----------------------------------------------

const lockLocals = {}

/**
 * 尝试执行hook方法，当获取到锁时执行,完成后自行释放并返回true，失败时不执行，返回false
 * @param key
 * @param hook
 * @returns {boolean} 成功执行返回true，失败返回true
 */
function attempt(key, hook) {
    if (!lock(key)) return false
    hook && hook()
    unlock()
    return true
}

/**
 * 获取锁
 * @param key 锁标识
 * @returns {boolean} 当锁被占用时返回false，成功获取返回true
 */
function lock(key) {
    return !lockLocals[key] && (lockLocals[key] = true)
}

/**
 * 释放锁
 * @param key 锁标识
 * @param delay 延时解锁，单位秒
 */
function unlock(key, delay) {
    delay ? setTimeout(() => lockLocals[key] = false, delay * 1000) : lockLocals[key] = false
}

//---------------------------------------------- ajax操作 ----------------------------------------------

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
    let index = load && loading()
    layui.$.post(url, data, ({code, info, data}) => {
        load && loaded(index)
        if (fail(code)) return warn(info)
        cb({code, info, data})
    })
}

//---------------------------------------------- 页面操作 ----------------------------------------------

/**
 * 跳转页面
 */
function goto(href) {
    window.location.href = href
}


/**
 * 刷新表格
 */
function reloadTable(table = layui.table) {
    table.reload(tableId)
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
}


//---------------------------------------------- 通知操作 ----------------------------------------------

/**
 * 显示加载蒙层
 */

function loading() {
    return layer.load(2)
}

/**
 * 关闭加载蒙层
 */
function loaded(index) {
    layer.close(index)
}

/**
 * 显示警告
 * @param msg 警告内容
 */
function warn(msg) {
    layer.msg(msg, {icon: 7})
    return false
}


/**
 * 显示成功通知
 *
 * @param cb 回调函数 默认刷新当前表单
 * @param msg 通知的消息
 */
function msg(cb, msg = '操作成功') {
    layer.msg(msg, {icon: 1, time: 500}, cb)
}


/**
 * 显示父级成功通知。当前窗口关闭，提醒依然存在
 *
 * @param cb 回调函数 默认刷新当前表单
 * @param msg 通知的消息
 */
function parentMsg(cb, msg = '操作成功') {
    parent.layer.msg(msg, {icon: 1, time: 500}, cb);
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

/**
 * 确认框
 */
function confirm({msg = '确认执行该操作吗', cb}) {
    layer.confirm(msg, {
        btn: ['确定', '取消'],
        shade: [0.1, '#fff']
    }, cb)
}

