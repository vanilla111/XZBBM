<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String basePath = "http://wx.yyeke.com/XZBBM/";
    //String basePath = "http://bbm.redleaf.wang/";
%>
<html>

<head>
    <title>学霸帮帮忙</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/homepage.css">
</head>

<body>
    <main>
        <div class="contianer" id="main">
            <div class="loginpage">
                <div class="toplog">
                    <img class="tuanlog" src="<%=basePath%>imgs/tuanlog.png">
                    <img class="schoollog" src="<%=basePath%>imgs/schoollog.png">
                </div>
                <div class="log"></div>
                <img class="logwords" src="<%=basePath%>imgs/logwords.png">
                <div class="loginbtn"></div>
                <div class="bottomlog">
                <img  src="<%=basePath%>imgs/bottomlog.png">
            </div>
            </div>
            <!-- 导航栏 -->
            <div class="nav">
                <div class="list">
                    <div class="headpage"></div>
                    <div class="qPage"></div>
                    <div class="sPage"></div>
                    <div class="myPage"></div>
                </div>
            </div>
            <!-- 首页 -->
            <div class="contents">
                <div class="questions">
                    <div class="newq">
                        <p>最新问题</p>
                    </div>
                    <div class="hotq">
                        <p>最热问题</p>
                    </div>
                </div>
                <div class="question_contents">
                    <div class="allnew">
                        <div class="newquestions">
                        </div>
                        <div class="nloadmore loadmore"></div>
                    </div>
                    <div class="allhot">
                        <div class="hotquestions">
                        </div>
                        <div class="hloadmore loadmore"></div>
                    </div>
                </div>
                <div class="toheight"></div>
            </div>
            <!-- 问题详情 -->
            <div class="qdetails">
                <div class="top">
                    <div class="topContent">
                        <img class="back" src="<%=basePath%>imgs/back.png">
                        <div class="wandc">
                            <p class="comments">评论:</p>
                            <p class="qcommentNum">2</p>
                        </div>
                    </div>
                </div>
                <div class="question_qdetails">
                    <div class="qdetails_content">
                        <div class="qImg">
                            <img class="qdetails_imgs qdetails_img" src="">
                        </div>
                        <div class="qdetails_title"></div>
                        <div class="qdetails_contents">
                            <p>这里是问题问题问题问题问题问题问题问题问题问题问题问题</p>
                        </div>
                        <div class="qdetails_bottom">
                            <div class="qdetails_avatar"><img class="qdetailshead" src=""></div>
                            <div class="info">
                                <div class="qdetails_name">
                                    <p>一只秋田猪</p>
                                </div>
                                <div class="qdetails_college">
                                    <p>计算机</p>
                                </div>
                            </div>
                            <img class="replyImg" src="<%=basePath%>imgs/reply.png">
                        </div>
                    </div>
                </div>
                <div class="replys">
                    <div class="reply">
                        <div class="replyContents">
                        </div>
                        <div class="replyContents">
                            <div class="reply_avatar"></div>
                            <div class="reply_content">
                                <div class="reply_top">
                                    <p class="reply_name">重邮小学生</p>
                                    <img class="took" src="<%=basePath%>imgs/took.png">
                                </div>
                                <div class="replyWords">
                                    <p>我说这样做，你就跟我这样做</p>
                                </div>
                                <div class="reply_Img">
                                    <img class="qdetails_imgs" src="">
                                </div>
                                <div class="reply_bottom">
                                    <p class="datetime">2017-12-07</p>
                                    <p class="hourtime">20:35</p>
                                    <p class="reply_college">计算机学院</p>
                                    <div class="reply_likeit like">
                                        <div class="likeIcon"></div>
                                        <p class="likeNum">20</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 问题详情图片放大 -->
                <div class="biggerimgs">
                    <div class="mask"></div>
                    <div class="biggerimgbox">
                        <img class="biggerimg" src=" ">
                    </div>
                </div>
                <!-- 回答问题页面 -->
                <div class="answerquestion">
                    <div class="answerq">
                        <div class="answerquestion_top">
                            <img class="backto" src="<%=basePath%>imgs/back.png">
                        </div>
                        <div class="answer_content">
                            <textarea name="answerContent" id="answerContent"></textarea>
                        </div>
                        <div class="upphotos">
                            <div class="upphoto">
                                <input id="upphotoload" type="file" accept="image/*">
                            </div>
                            <div class="upphotobox imgbox">
                                <img class="deleteimg" src="<%=basePath%>imgs/closed.png">
                                <img class="photoshow">
                            </div>
                        </div>
                        <div class="sendanswer">发送</div>
                        <div class="answerquestion_bottom">
                            <img src="<%=basePath%>imgs/bottom.png">
                        </div>
                    </div>
                </div>
                <div class="thisid" style="display: none;"></div>
            </div>
            <!-- 我的 -->
            <div class="my_page">
                <div class="background">
                    <div class="my_pageTop">
                        <div class="topbox"></div>
                        <div class="me">
                            <div class="myatavar">
                                <img class="myhead myHead" src="">
                            </div>
                            <div class="myId">
                                <p>我就是学霸</p>
                            </div>
                        </div>
                        <div class="contentop">
                            <div class="myq">
                                <p>我的提问</p>
                            </div>
                            <div class="myanswer">
                                <p>我的回答</p>
                            </div>
                        </div>
                    </div>
                    <div class="mycontents">
                        <div class="allmyq">
                            <div class="myquestion_contents"></div>
                            <div class="myqloadmore loadmore"></div>
                        </div>
                        <div class="allmya">
                            <div class="myanswer_contents"></div>
                            <div class="myaloadmore loadmore"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 提问页面 -->
            <div class="question_Page">
                <div class="question_Page_contents">
                    <div class="qtitle">
                        <input type="text" name="qtitle" id="qtitle" placeholder="输入标题">
                    </div>
                    <div class="qwords">
                        <textarea name="qwords" id="qwords" placeholder="请输入题目介绍（不得超过100字）"></textarea>
                    </div>
                    <div class="uploadBtn">
                        <input id="upload" type="file" accept="image/*">
                    </div>
                    <div class="imageshowbox imgbox">
                        <img class="qdeleteimg" src="<%=basePath%>imgs/closed.png">
                        <img class="imageshow">
                    </div>
                    <div class="allcolleges">
                        <div class="choseCollege">通信</div>
                        <div class="choseCollege">计算机</div>
                        <div class="choseCollege">自动化</div>
                        <div class="choseCollege">先进</div>
                        <div class="choseCollege">光电</div>
                        <div class="choseCollege">软件</div>
                        <div class="choseCollege">生物</div>
                        <div class="choseCollege">理学院</div>
                        <div class="choseCollege">经管</div>
                        <div class="choseCollege">传媒</div>
                        <div class="choseCollege">外国语</div>
                        <div class="choseCollege">国际</div>
                        <div class="choseCollege">安法</div>
                        <div class="choseCollege">体育</div>
                        <div class="emmm"></div>
                        <div class="emmm"></div>
                    </div>
                    <div class="sendit">发送</div>
                </div>
            </div>
            <!-- 搜索页面 -->
            <div class="search_Page">
                <div class="search_Page_contents">
                    <div class="inputbox" contenteditable="true">
                        <img src="<%=basePath%>imgs/searchIcon.png">
                        <input type="search" name="searchBox" id="searchBox" placeholder="寻找你想要的">
                    </div>
                    <p class="collegeName">学院:</p>
                    <div class="allcolleges">
                        <div class="searchCollege">通信</div>
                        <div class="searchCollege">计算机</div>
                        <div class="searchCollege">自动化</div>
                        <div class="searchCollege">先进</div>
                        <div class="searchCollege">光电</div>
                        <div class="searchCollege">软件</div>
                        <div class="searchCollege">生物</div>
                        <div class="searchCollege">理学院</div>
                        <div class="searchCollege">经管</div>
                        <div class="searchCollege">传媒</div>
                        <div class="searchCollege">外国语</div>
                        <div class="searchCollege">国际</div>
                        <div class="searchCollege">安法</div>
                        <div class="searchCollege">体育</div>
                        <div class="emmm"></div>
                        <div class="emmm"></div>
                    </div>
                </div>
            </div>
            <div class="searchshow">
                <div class="answerquestion_top">
                    <img class="backtosearch" src="<%=basePath%>imgs/back.png">
                </div>
                <div class="searchcontents"></div>
            </div>
        </div>
    </main>
    <script type="text/javascript" src="<%=basePath%>javascript/homepage.js"></script>
    <script type="text/javascript" src="<%=basePath%>javascript/touch.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>javascript/cat.touchjs.js"></script>
    <script type="text/javascript" src="<%=basePath%>javascript/jquery-1.10.2.min.js"></script>
    <script type="text/javascript">
    $(function() {
        var $targetObj = $('.biggerimg');
        //初始化设置
        cat.touchjs.init($targetObj, function(left, top, scale, rotate) {
            $('#left').text(left);
            $('#top').text(top);
            $('#scale').text(scale);
            $('#rotate').text(rotate);
            $targetObj.css({
                left: left,
                top: top,
                'transform': 'scale(' + scale + ') rotate(' + rotate + 'deg)',
                '-webkit-transform': 'scale(' + scale + ') rotate(' + rotate + 'deg)'
            });
        });
        //初始化缩放手势（不需要就注释掉）
        cat.touchjs.scale($targetObj, function(scale) {
            $('#scale').text(scale);
        });
    });
    //保存并刷新
    function save() {
        var data = {
            left: cat.touchjs.left,
            top: cat.touchjs.top,
            scale: cat.touchjs.scaleVal,
            rotate: cat.touchjs.rotateVal
        };
        //本地存储
        window.localStorage.cat_touchjs_data = JSON.stringify(data);
        window.location = window.location;
    };
    //重置
    function reset() {
        var data = {
            left: 0,
            top: 0,
            scale: 1,
            rotate: 0
        };
        //本地存储
        window.localStorage.cat_touchjs_data = JSON.stringify(data);
    };
    document.querySelector('.mask').onclick = function() {
        document.querySelector('.biggerimgs').style.display = 'none';
        reset();
    }
    </script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="https://wx.idsbllp.cn/wx-api/share.js"></script>
    <script>
            WXSHARE.config({debug: false});
            WXSHARE.ready(function() {
                var option = {
                      title: "学霸帮帮忙 | 重邮200名学霸在此！从此，期末复习So Easy！",
                      link: "http://wx.yyeke.com/XZBBM/bbm/page",
                      desc: '亲，快来参加学霸帮帮忙——网络志愿服务活动吧！',
                      type: '',
                      success: function() {
                          console.log('分享成功');
                      },
                      cancel: function() {
                          console.log('取消分享');
                      },
                  };

                  wx.onMenuShareTimeline(option);
                  wx.onMenuShareAppMessage(option);
                  wx.onMenuShareQQ(option);
                  wx.onMenuShareWeibo(option);
                  wx.onMenuShareQZone(option);
            });

        </script>

</body>

</html>