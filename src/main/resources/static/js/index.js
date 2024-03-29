layui.use(['layer', 'element', 'jquery'], () => {
        const {element, $} = layui
        const root = $('#root')
        const loseSider = 'lose-sider'
        let currentTabId
        let tabSpreadTimer

        element.on('nav(layout-sider)', tabHandle)
        element.on('nav(user-aux-group)', tabHandle)
        element.on(`tab(layout-tab)`, ({elem}) => {
            let id = $(elem.context).attr('lay-id')
            if (!id || currentTabId === id) return

            $('.layout-sider .layui-this,.layout-main .header .layui-this').removeClass('layui-this')
            $(`[data-id=${id}]`).parent().addClass('layui-this')
            element.tabChange('nav', id)
        })
        element.on('tabDelete(layout-tab)', () => {
            // tabPrevPage()
        })


        function tabHandle({context}) {
            let nav = $(context),
                url = nav.data('url')
            if (!url) return
            changeTab({
                url,
                id: currentTabId = nav.data('id'),
                name: `tab-${nav.data('title')}`,
                title: nav.data('title'),
                onAdd: tabNextPage
            })
            root.removeClass(loseSider)
        }


        const tabTitle = $('.layout-main>.body>.layui-tab .layui-tab-title')
        $('.tab-tool>#next-page').click(tabNextPage)
        $('.tab-tool>#prev-page').click(tabPrevPage)

        // 标签下一页
        function tabNextPage() {
            rollPage('right')
            // let count = 0
            // const nextLeft = parseFloat(tabTitle.css('left')) - tabTitle.width()
            // for (let {offsetWidth: width} of tabTitle.children('li')) {
            //     if (count -= width, count < nextLeft) {
            //         return acquire('tab-lock', () => tabTitle.css('left', count + width), 210)
            //     }
            // }
        }

        // 标签上一页
        function tabPrevPage() {
            rollPage('left')
            // const left = parseFloat(tabTitle.css('left'))
            // if (!left) return
            // let count = 0, pageCount = 0
            // const titleWidth = tabTitle.width(), prevLeft = left + titleWidth
            // for (let {offsetWidth: width} of tabTitle.children('li')) {
            //     if (pageCount += width, pageCount > titleWidth) {
            //         count -= (pageCount - width)
            //         if (count === left) {
            //             return acquire('tab-lock', () => tabTitle.css('left', 0), 210)
            //         } else if (count < prevLeft) {
            //             return acquire('tab-lock', () => tabTitle.css('left', count), 210)
            //         }
            //         pageCount = width
            //     }
            // }
        }

        function rollPage(e) {
            var tabs = tabTitle.children("li"),
                tabWidth = tabTitle.width(),
                left = parseFloat(tabTitle.css("left")),
                padding = parseFloat(tabTitle.css("padding-left")) + parseFloat(tabTitle.css("padding-right"))
            console.log(padding)
            console.log(tabWidth)
            if ("left" === e) {

                tabWidth -= 150
                if (!left && left <= 0) return
                var prevLeft = -left + 100 - tabWidth;
                console.log('start')
                tabs.each((e, t) => {
                    var li = $(t), liLeft = li.position().left;
                    console.log('li:' + li.children('.text').html())
                    console.log('liLeft:' + liLeft)
                    console.log('r:' + prevLeft)
                    if (liLeft >= prevLeft) return tabTitle.css("left", -liLeft)
                })
            } else if ("auto" === e) {

                var e, r = tabs.eq(t);
                if (r[0]) {
                    if (e = r.position().left, e < -left) return tabTitle.css("left", -e);
                    if (e + r.outerWidth() >= tabWidth - left) {
                        var o = e + r.outerWidth() - (tabWidth - left);
                        tabs.each(function (e, t) {
                            var n = $(t), l = n.position().left;
                            if (l + left > 0 && l - left > o) return tabTitle.css("left", -l), !1
                        })
                    }
                }
            } else {
                tabs.each(function (e, t) {
                    var li = $(t), liLeft = li.position().left -100;
                    if (liLeft + li.width()  >= tabWidth) return tabTitle.css("left", -liLeft )
                })
            }
        }


        // logo 跳首页
        $('.logo').click(() => element.tabChange('layout-tab', 'home'))

        $('#sider-trigger').click(() => root.hasClass(loseSider) ? root.removeClass(loseSider) : root.addClass(loseSider))

        // 手机端关闭菜单
        $('#layout-sider-mask').click(() => root.removeClass(loseSider))

        // 刷新页面
        $('#refresh-content').click(() => {
            let frame = $('.layout-main > .body > .layui-tab > .layui-tab-content > .layui-show>iframe')
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


        $(document.body).on('mouseleave', `#layout-tab>.layui-tab-title>li`, function () {
            tabSpreadTimer ? clearTimeout(tabSpreadTimer) : $(this).find('.text-spread').fadeOut()
        }).on('mouseenter', `#layout-tab>.layui-tab-title>li`, function () {
            tabSpreadTimer = setTimeout(() => {
                $(this).find('.text-spread').fadeIn()
                tabSpreadTimer = false
            }, 600)
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
 * @param onAdd 新建标签时的回调
 */
function changeTab({id, url, title, name, onAdd}) {
    const {$, element} = layui
    let opened = $(`#layout-tab > .layui-tab-title > li[lay-id=${id}]`)
    if (!opened.length) {
        element.tabAdd('layout-tab', {
            id,
            title: `<div class="text">${title}<div><div class="text-spread">${title}</div>`,
            content: `<iframe src="${url}" name="${name}" class="iframe" data-id="${id}"></iframe>`
        })
        onAdd && onAdd()
    }
    element.tabChange('layout-tab', id)
}