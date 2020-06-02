layui.use(['layer', 'element', 'jquery'], () => {
    const {element, jquery: $} = layui
    const mainLayout = $('#main-layout')

    element.on('nav(leftNav)', navHandle)
    element.on('nav(rightNav)', navHandle)

    // 菜单控制
    $('#hide-menu').click(() => {
        mainLayout.hasClass('hide-side') ? -mainLayout.removeClass('hide-side') : mainLayout.addClass('hide-side')
    })

    // 手机端关闭菜单
    $('.main-mask').click(()=>{
        mainLayout.removeClass('hide-side')
    })


    // 刷新页面
    $('#refresh-btn').click(() => {
        let current = $(".layui-tab-item.layui-show>iframe")
        current.attr('src', current.attr('src'))
    })

    function navHandle(obj) {
        let nav = $(obj.context),
            url = nav.data('url')
        if (!url) return

        let id = nav.data('id'),
            text = nav.data('text')

        let opened = $('.main-layout-tab .layui-tab-title').find("li[lay-id=" + id + "]");
        if (opened.length > 0) {
            // 已打开进行切换
            element.tabChange('tab', id)
        } else {
            element.tabAdd('tab', {
                id: id,
                title: text,
                content: `<iframe src="${url}" name="iframe${id}" class="iframe" data-id="${id}"></iframe>`
            });
            element.tabChange('tab', id)
        }
        mainLayout.removeClass('hide-side')
    }


    //示范一个公告层
//	layer.open({
//		  type: 1
//		  ,title: false //不显示标题栏
//		  ,closeBtn: false
//		  ,area: '300px;'
//		  ,shade: 0.8
//		  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
//		  ,resize: false
//		  ,btn: ['火速围观', '残忍拒绝']
//		  ,btnAlign: 'c'
//		  ,moveType: 1 //拖拽模式，0或者1
//		  ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">后台模版1.1版本今日更新：<br><br><br>数据列表页...<br><br>编辑删除弹出功能<br><br>失去焦点排序功能<br>数据列表页<br>数据列表页<br>数据列表页</div>'
//		  ,success: function(layero){
//		    var btn = layero.find('.layui-layer-btn');
//		    btn.find('.layui-layer-btn0').attr({
//		      href: 'http://www.layui.com/'
//		      ,target: '_blank'
//		    });
//		  }
//		});
})