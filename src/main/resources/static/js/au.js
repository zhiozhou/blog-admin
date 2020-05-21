
// 图片上传参数
const UPLOAD_URL = 'https://zhousb.cn/file-upload/file/multipart',
    UPLOAD_PREFIX = 'zhousb-admin'

const
    // tinymce富文本默认
    TINYMCE_STAGE = {
        selector: '.tinymce',
        language: "zh_CN",
        height : 300,
        menubar: false,
        statusbar: false,
        plugins: 'lists , hr ,image,link , autolink , fullscreen',
        toolbar: 'undo redo | bold italic | styleselect forecolor | bullist numlist hr | image link | fullscreen',
        images_upload_handler: function (blobInfo, succFun, failFun) {
            let file = blobInfo.blob(),
                formData = new FormData();
            formData.append('prefix', UPLOAD_PREFIX)
            formData.append('file', file, file.name);
            $.ajax({
                url: UPLOAD_URL,
                type: "POST",
                data: formData,
                processData: false,  // 不处理数据
                contentType: false,   // 不设置内容类型
                success: ({code,info,data}) =>{           //成功回调
                    if(fail(code)){
                        failFun(info);
                    }else{
                        succFun(data[0].origin);
                    }
                }
            })
        },
        init_instance_callback : (editor)=> {
            document.getElementById(`${editor.id}-load`).remove()
        }
    }

/**
 * 页面提交
 */
function PAGE_SUBMIT({form, field: data}){
    post(prefix+form.getAttribute('action'),data,()=>{
        done(()=>{
            window.location.href = document.getElementById('superior').getAttribute('href')
        })
    })
    return false
}

/**
 * iframe提交
 */
function IFRAME_SUBMIT({form, field: data}) {
    post(prefix+form.getAttribute('action'),data,()=>{
        outDone()
    })
    return false
}

function SINGLE_IMG(elem){
    return {
        elem: elem,
        url: UPLOAD_URL,
        data: {prefix: UPLOAD_PREFIX},
        acceptMime: 'image/*',
        before:()=>{loading()},
        done: ({code,info,data})=>{
            if (fail(code)) return warn(info)
            done(null,'上传成功')
            let src =   data[0].origin
            layui.jquery(`${elem} .view`).show().attr('src', src).next().val(src)
            layui.jquery(`${elem} .tips`).hide()
            loaded()
        }
    }
}


function SINGLE_FILE(elem){
    return {
        elem: elem,
        url: UPLOAD_URL,
        data: {
            prefix: UPLOAD_PREFIX,
            keepName: true
        },
        accept: 'file',
        choose: (obj)=>{
            obj.preview((index, file)=>{
                layui.jquery(elem).siblings('.view').html(file.name)
            });
        },
        before:()=>{loading()},
        done: ({code,info,data})=>{
            if (fail(code)) return warn(info)
            done(null,'上传成功')
            let url  = data[0].origin
            layui.jquery(elem)
                .siblings('.view').attr('href',url)
                .next().val(url)
            loaded()
        }
    }
}