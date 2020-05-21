layui.config({
    base: gate + 'plugin/layui/extend/'
});
layui.use(['jquery', 'util', 'layarea'], function () {
    const {jquery: $, layarea} = layui

    // 初始化地区三级联动
    layarea.render({
        elem: '#area-picker'
    });

    // 初始化时间
    $('.date-field').each((index, field) => {
        $(field).val(formatDate($(field).val(),$(field).data('format')))
    })

});



// frame窗口大小参数
const sizePC = ['700px', '620px'],
    sizeMobile = ['320px', '620px']
function newFrame(title, url) {
    let pc = window.innerWidth > 768

    let index = layer.open({
        type: 2,
        title: title,
        area: pc ? sizePC : sizeMobile,
        fixed: false, //不固定
        content: url
    })

    // 手机端全屏
    if (!pc) {
        layer.full(index);
    }
}

const iframe = window.frameElement,
    iframeName = iframe && iframe.getAttribute('name')


// 刷新子页,关闭弹窗
function refresh() {
    //根据传递的name值，获取子iframe窗口，执行刷新
    window.frames[iframeName] ?
        window.frames[iframeName].location.reload() :
        window.location.reload()
    layer.closeAll();
}


// 校验返回码
function fail(code) {
    return '0000' !== code
}


// 加载蒙层
var loaderIndex

function loading() {
    loaderIndex = layer.load(2)
}

// 关闭壁蒙层
function loaded() {
    layer.close(loaderIndex)
}

// 通用警告
function warn(content) {
    layer.msg(content, {icon: 7});
    return false
}

// 通用操作成功
function done(cb = refresh,msg = '操作成功') {
    layer.msg(msg, {icon: 1, time: 500}, cb);
}


// 调用父级提醒，当前iframe关闭时，提醒会存在
function outDone() {
    parent.layer.msg('操作成功', {icon: 1, time: 500}, closeThis);
}

// 关闭当前iframe
function closeThis() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
    parent.location.reload();
}


// 封装post方法 带有蒙层
function post(u, d, cb) {
    loading()
    layui.jquery.post(u, d, ({code, info, data}) => {
        loaded()
        if (fail(code)) {
            return warn(info)
        }
        cb(data)
    })
}

function formatDate(date,format ='yyyy年MM月dd日 HH:mm:ss' ) {
    return layui.util.toDateString(date, format)
}









