//---------------------------------------------- 通用配置 ----------------------------------------------

/**
 * tinymce 富文本基通用配置
 */
function tinymceRender(tinymce, options) {
    tinymce.init({
        ...{
            selector: '.tinymce',
            language: "zh_CN",
            convert_urls: false,
            height: 300,
            menubar: false,
            statusbar: false,
            plugins: 'lists , hr ,image,table,link , autolink , fullscreen',
            toolbar: 'undo redo | bold italic | styleselect forecolor | bullist numlist hr | image table link | fullscreen',
            images_upload_handler: function (blobInfo, successCB, failCB) {
                let file = blobInfo.blob(),
                    formData = new FormData();
                formData.append('prefix', _upload.prefix)
                formData.append('file', file, file.name)
                layui.jquery.ajax({
                    url: _upload.url,
                    type: "POST",
                    data: formData,
                    processData: false,  // 不处理数据
                    contentType: false,   // 不设置内容类型
                    success: ({code, info, data}) => {
                        fail(code) ? failCB(info) : successCB(data[0].origin)
                    }
                })
            },
            init_instance_callback: (editor) => {
                document.getElementById(`${editor.id}-load`).remove()
            }
        },
        ...options
    })
}


/**
 * layui 文件上传通用配置
 */
function uploadRender(upload, options) {
    return upload.render({
        ...{
            url: `${_upload.url}upload/multipart`,
            data: {
                prefix: _upload.prefix,
                keepName: true
            },
            accept: 'file',
            choose: (obj) => {
                obj.preview((index, file) => layui.jquery(options.elem).siblings('.view').html(file.name))
            },
            before: () => loading(),
            done: ({code, info, data}) => {
                loaded()
                if (fail(code)) return warn(info)
                done(null, '上传成功')
                let url = data[0].origin
                layui.jquery(options.elem).siblings('.view').attr('href', url).next().val(url)
            }
        },
        ...options
    })
}


/**
 * 压缩图片，返回dataURL格式
 */
const m2 = 2 * 1024 * 1024;

function compress(data, cb) {
    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    var img = new Image();
    img.src = data;
    img.onload = function () {
        var width = img.width;
        var height = img.height;
        var ration = Math.sqrt(width * height / m2);
        width /= ration;
        height /= ration;
        canvas.width = width;
        canvas.height = height;
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
        // 将canvas中的图片转化为base64格式

        console.log('aaa')
        cb && cb(canvas.toDataURL('image/jpeg', 0.92));
    }
}

function dataURLtoFile(dataURL) {
    return blobToFile(dataURLtoBlob(dataURL))
}

function dataURLtoBlob(dataURL) {
    var arr = dataURL.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n)
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
    }
    return new Blob([u8arr], {type: mime})
}

function blobToFile(theBlob, fileName) {
    theBlob.lastModifiedDate = new Date()
    theBlob.name = fileName
    return theBlob
}


//---------------------------------------------- 通用表单提交 ----------------------------------------------

/**
 * 页面形式的表单提交，成功后跳转前一级标签
 */
function pageSubmit({form, field: data}) {
    httpPost({
        url: prefix + form.getAttribute('action'),
        data,
        cb: () => done(() => {
            window.location.href = layui.jquery('.layui-form>.layui-tab .layui-this').prev().children('a').attr('href')
        })
    })

    return false
}



/**
 * 内联页面通用提交，成功后刷新父级表格
 */
function iframeSubmit({form, field: data}) {
    httpPost({
        url: prefix + form.getAttribute('action'),
        data,
        cb: () => outDone(() => {
            parent.layer.close(parent.layer.getFrameIndex(window.name))
            parent.layui.table.reload('table')
        })
    })
    return false
}

