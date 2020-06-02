//---------------------------------------------- 通用配置 ----------------------------------------------

/**
 * 图片上传地址
 */
const UPLOAD_URL = 'https://zhousb.cn/file-upload/file/multipart'

/**
 * 图片上传的标识前缀
 */
const UPLOAD_PREFIX = 'zhousb-admin'

/**
 * tinymce 富文本基通用配置
 */
const TINYMCE_STAGE = {
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
        formData.append('prefix', UPLOAD_PREFIX)
        formData.append('file', file, file.name)
        layui.jquery.ajax({
            url: UPLOAD_URL,
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
}

/**
 * layui 的单个图片上传的配置
 */
function SINGLE_IMG(elem) {
    return {
        elem: elem,
        url: UPLOAD_URL,
        data: {prefix: UPLOAD_PREFIX},
        acceptMime: 'image/*',
        before: () => {
            loading()
        },
        done: ({code, info, data}) => {
            if (fail(code)) return warn(info)
            done(null, '上传成功')
            let src = data[0].origin
            layui.jquery(`${elem} .view`).show().attr('src', src).next().val(src)
            layui.jquery(`${elem} .tips`).hide()
            loaded()
        }
    }
}


/**
 * layui 的单个文件上传的配置
 */
function SINGLE_FILE(elem) {
    return {
        elem: elem,
        url: UPLOAD_URL,
        data: {
            prefix: UPLOAD_PREFIX,
            keepName: true
        },
        accept: 'file',
        choose: (obj) => {
            obj.preview((index, file) => {
                layui.jquery(elem).siblings('.view').html(file.name)
            })
        },
        before: () => {
            loading()
        },
        done: ({code, info, data}) => {
            if (fail(code)) return warn(info)
            done(null, '上传成功')
            let url = data[0].origin
            layui.jquery(elem)
                .siblings('.view').attr('href', url)
                .next().val(url)
            loaded()
        }
    }
}


//---------------------------------------------- 通用表单提交 ----------------------------------------------

/**
 * 页面形式的表单提交，成功后跳转 #superior 的href页
 */
function PAGE_SUBMIT({form, field: data}) {
    post(prefix + form.getAttribute('action'), data, () => {
        done(() => {
            window.location.href = document.getElementById('superior').getAttribute('href')
        })
    })
    return false
}

/**
 * iframe页面内的提交，成功后刷新父级表格
 */
function IFRAME_SUBMIT({form, field: data}) {
    post(prefix + form.getAttribute('action'), data, () => {
        outDone(() => {
            parent.layer.close(parent.layer.getFrameIndex(window.name))
            parent.layui.table.reload('table', {
                where: null,
                page: {curr: 1}
            })
        })
    })
    return false
}

