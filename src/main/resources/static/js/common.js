
/**
 * 图片上传地址
 */
// const UPLOAD_URL = 'https://zhousb.cn/file-upload/file/multipart'
const UPLOAD_URL = 'http://127.0.0.1:8889/file/upload/multipart'

/**
 * 图片上传的标识前缀
 */
const UPLOAD_PREFIX = 'zhousb-admin'




layui.config({
    base: gate + 'plugin/layui/extend/'
})

/**
 * 全局初始化方法
 */
layui.use(['jquery', 'util', 'layarea'], function () {
    const {jquery: $, layarea} = layui

    // 初始化地区三级联动
    layarea.render({
        elem: '#area-picker'
    })

    // 初始化时间
    $('.date-field').each((index, field) => {
        $(field).val(formatDate($(field).val(), $(field).data('format')))
    })

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
 * 创建 iframe 窗口，手机端自动全屏
 * @param title 窗口标题
 * @param url 地址
 */
function newFrame(title, url) {
    const sizePC = ['700px', '620px'],
        sizeMobile = ['320px', '620px']
    let isPc = window.innerWidth > 768
    let index = layer.open({
        type: 2,
        title: title,
        area: isPc ? sizePC : sizeMobile,
        fixed: false, //不固定
        content: url
    })
    // 手机端全屏
    if (!isPc) layer.full(index)
}

/**
 * 校验操作是否失败
 * @param code 返回码
 */
function fail(code) {
    return '0000' !== code
}

/**
 * 自动加载框的 post 请求
 * @param u 请求地质
 * @param d 请求数据
 * @param cb 成功的回调函数，失败则使用警告提醒
 */
function post(u, d, cb) {
    loading()
    layui.jquery.post(u, d, ({code, info, data}) => {
        loaded()
        if (fail(code)) return warn(info)
        cb(data)
    })
}


//---------------------------------------------- 通知类 ----------------------------------------------

/**
 * 显示加载蒙层
 */
let loaderIndex

function loading() {
    loaderIndex = layer.load(2)
}

/**
 * 关闭加载蒙层
 */
function loaded() {
    layer.close(loaderIndex)
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
function done(cb, msg = '操作成功') {
    layer.msg(msg, {icon: 1, time: 500}, cb)
}


/**
 * 显示父级成功通知。当前窗口关闭，提醒依然存在
 *
 * @param cb 回调函数 默认刷新当前表单
 * @param msg 通知的消息
 */
function outDone(cb, msg = '操作成功') {
    parent.layer.msg('操作成功', {icon: 1, time: 500}, cb);
}












