layui.use(['layer', 'element', 'jquery'], () => {
    const {element, jquery: $} = layui
    const $root = $('#root')

    const tabKey = 'layout-tab'

    let currentTabId

    element.on('nav(layout-sider)', navHandle)
    element.on('nav(user-aux-group)', navHandle)
    element.on(`tab(${tabKey})`, ({elem}) => {
        let id = $(elem.context).attr('lay-id')
        if (!id || currentTabId === id) return

        $('#layout-sider .layui-this,#layout-header .layui-this').removeClass('layui-this')
        $(`[data-id=${id}]`).parent().addClass('layui-this')
        element.tabChange('nav', id)
    })

    function navHandle({context}) {
        let $nav = $(context),
            url = $nav.data('url')
        if (url) {
            currentTabId = $nav.data('id')
            let opened = $(`#${tabKey} .layui-tab-title`).find(`li[lay-id=${currentTabId}]`)

            0 === opened.length && element.tabAdd(tabKey, {
                id: currentTabId,
                title: $nav.data('text'),
                content: `<iframe src="${url}" name="iframe${currentTabId}" class="iframe" data-id="${currentTabId}"></iframe>`
            })
            element.tabChange(tabKey, currentTabId)
            $root.removeClass(loseSider)
        }
    }


    // logo 跳首页
    $('.logo').click(() => {
        element.tabChange(tabKey, 'home')
    })


    const loseSider = 'lose-sider'
    $('#sider-trigger').click(() => {
        $root.hasClass(loseSider) ? $root.removeClass(loseSider) : $root.addClass(loseSider);
    })

    // 手机端关闭菜单
    $('#layout-sider-mask').click(() => {
        $root.removeClass(loseSider)
    })

    // 刷新页面
    $('#refresh-content').click(() => {
        let frame = $('.layui-tab-item.layui-show>iframe')
        frame.attr('src', frame.attr('src'))
    })

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