const tabKey = 'layout-tab'
layui.use(['layer', 'element', 'jquery'], () => {
        const {element, jquery: $} = layui
        const root = $('#root')
        const loseSider = 'lose-sider'
        let currentTabId
        let tabSpreadTimer

        element.on('nav(layout-sider)', navHandle)
        element.on('nav(user-aux-group)', navHandle)
        element.on(`tab(${tabKey})`, ({elem}) => {
            let id = $(elem.context).attr('lay-id')
            if (!id || currentTabId === id) return

            $('.layout-sider .layui-this,.layout-main .header .layui-this').removeClass('layui-this')
            $(`[data-id=${id}]`).parent().addClass('layui-this')
            element.tabChange('nav', id)
        })

        function navHandle({context}) {
            let nav = $(context),
                url = nav.data('url')
            if (!url) return
            changeTab({
                url,
                id: currentTabId = nav.data('id'),
                name: `tab-${nav.data('title')}`,
                title: nav.data('title'),
            })
            root.removeClass(loseSider)
        }

        $('.tab-tool #next-page').click(() => {
            let title = $('.layout-main>.body>.layui-tab .layui-tab-title'),
                width = title.width(),
                left = parseFloat(title.css('left'))

            let r = 0

            for (let li of title.children('li')) {
                let tabWidth = $(li).outerWidth()
                if (r + tabWidth > width) {
                    title.css('left', -r)
                    return
                } else {
                    r += tabWidth
                }
            }

            // .each((_,li)=>{
            //
            //  })


            // var i = a("#LAY_app_tabsheader"), n = i.children("li"), l = (i.prop("scrollWidth"), i.outerWidth()),
            //     s = parseFloat(i.css("left"));
            // if ("left" === e) {
            //     if (!s && s <= 0) return;
            //     var r = -s - l;
            //     n.each(function (e, t) {
            //         var n = a(t), l = n.position().left;
            //         if (l >= r) return i.css("left", -l), !1
            //     })
            // } else "auto" === e ? !function () {
            //     var e, r = n.eq(t);
            //     if (r[0]) {
            //         if (e = r.position().left, e < -s) return i.css("left", -e);
            //         if (e + r.outerWidth() >= l - s) {
            //             var o = e + r.outerWidth() - (l - s);
            //             n.each(function (e, t) {
            //                 var n = a(t), l = n.position().left;
            //                 if (l + s > 0 && l - s > o) return i.css("left", -l), !1
            //             })
            //         }
            //     }
            // }() : n.each(function (e, t) {
            //     var n = a(t), r = n.position().left;
            //     if (r + n.outerWidth() >= l - s) return i.css("left", -r), !1
            // })
        })

        // logo 跳首页
        $('.logo').click(() => {
            element.tabChange(tabKey, 'home')
        })


        $('#sider-trigger').click(() => {
            root.hasClass(loseSider) ? root.removeClass(loseSider) : root.addClass(loseSider);
        })

        // 手机端关闭菜单
        $('#layout-sider-mask').click(() => {
            root.removeClass(loseSider)
        })

        // 刷新页面
        $('#refresh-content').click(() => {
            let frame = $('.layui-tab-item.layui-show>iframe')
            frame.attr('src', frame.attr('src'))
        })

        // 全屏
        const fullScreenPrefix =
            document.fullscreenEnabled ? true :
                document.msFullscreenEnabled ? 'ms' :
                    document.mozFullScreenEnabled ? 'moz' :
                        document.webkitFullscreenEnabled ? 'webkit' : false
        $('#full-screen').click(function () {
            if (!fullScreenPrefix) return warn('当前浏览器不支持全屏')
            let icon = $(this).children('.fa')
            icon.hasClass('fa-expand') ?
                icon.removeClass('fa-expand').addClass('fa-compress') &&
                document.body[true === fullScreenPrefix ? 'requestFullscreen' : `${fullScreenPrefix}RequestFullScreen`]()
                :
                icon.removeClass('fa-compress').addClass('fa-expand') &&
                document[true === fullScreenPrefix ? 'exitFullscreen' : `${fullScreenPrefix}ExitFullscreen`]()
        })


        $(document.body).on('mouseenter', `#${tabKey} .layui-tab-title li`, function () {
            tabSpreadTimer = setTimeout(() => {
                $(this).find('.text-spread').fadeIn()
                tabSpreadTimer = false
            }, 600)
        })

        $(document.body).on('mouseleave', `#${tabKey} .layui-tab-title li`, function () {
            tabSpreadTimer ? clearTimeout(tabSpreadTimer) : $(this).find('.text-spread').fadeOut()
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
    }
)

/**
 * 切换标签
 * @param id 标签id
 * @param url 跳转连接
 * @param title 显示名称
 * @param name iframe的name
 */
function changeTab({id, url, title, name}) {
    let opened = layui.jquery(`#${tabKey} .layui-tab-title li[lay-id=${id}]`)
    if (0 === opened.length) {
        layui.element.tabAdd(tabKey, {
            id,
            title: `<div class="text">${title}<div><div class="text-spread">${title}</div>`,
            content: `<iframe src="${url}" name="${name}" class="iframe" data-id="${id}"></iframe>`
        })
    }
    layui.element.tabChange(tabKey, id)
}