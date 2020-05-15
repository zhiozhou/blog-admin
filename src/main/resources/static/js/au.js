const
    // 页面提交
    PAGE_SUBMIT = ({form, field: data})=> {
        post(prefix+form.getAttribute('action'),data,()=>{
            done(()=>{
                window.location.href = document.getElementById('superior').getAttribute('href')
            })
        })
        return false
    },

    // iframe提交
    IFRAME_SUBMIT = ({form, field: data})=> {
        post(prefix+form.getAttribute('action'),data,()=>{
            outDone()
        })
        return false
    },

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

