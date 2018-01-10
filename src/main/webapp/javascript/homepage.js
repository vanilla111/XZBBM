var basePath = "http://wx.yyeke.com/XZBBM/bbm/";
var picPath = "http://wx.yyeke.com/XZBBM/";
// var basePath = "http://bbm.redleaf.wang/bbm/";
// var picPath = "http://bbm.redleaf.wang/";

var newq = document.querySelector('.newq');
var hotq = document.querySelector('.hotq');
var newquestions = document.querySelector('.newquestions');
var hotquestions = document.querySelector('.hotquestions');
var contents = document.querySelector('.contents');
var questionMiddle = document.querySelectorAll('.questionMiddle');
var qdetails = document.querySelector('.qdetails');
var comment = document.querySelectorAll('.comment');
var back = document.querySelector('.back');
var nav = document.querySelector('.nav');
var qwatchedNum = document.querySelector('.qwatchedNum');
var qcommentNum = document.querySelector('.qcommentNum');
var qImg = document.querySelector('.qImg');
var qdetails_avatar = document.querySelector(".qdetails_avatar");
var qdetails_contents = document.querySelector('.qdetails_contents');
var qdetails_name = document.querySelector('.qdetails_name');
var qdetails_college = document.querySelector('.qdetails_college');
var qdetails_title = document.querySelector('.qdetails_title');
var headpage = document.querySelector('.headpage');
var qPage = document.querySelector('.qPage');
var biggerimg = document.querySelector('.biggerimg');
var biggerimgs = document.querySelector('.biggerimgs');
var sPage = document.querySelector('.sPage');
var myPage = document.querySelector('.myPage');
var my_page = document.querySelector('.my_page');
var question_Page = document.querySelector('.question_Page');
var search_Page = document.querySelector('.search_Page');
var myq = document.querySelector('.myq');
var myanswer = document.querySelector('.myanswer');
var myquestion_contents = document.querySelector('.myquestion_contents');
var myanswer_contents = document.querySelector('.myanswer_contents');
var choseCollege = document.querySelectorAll('.choseCollege');
var searchCollege = document.querySelectorAll('.searchCollege');
var myquestion = document.querySelectorAll('.myquestion');
var my_answer = document.querySelectorAll('.my_answer');
var mask = document.querySelector('.mask');
var answerquestion = document.querySelector('.answerquestion');
var replyImg = document.querySelector('.replyImg');
var backto = document.querySelector('.backto');
var imageshow = document.querySelector(".imageshow");
var qdeleteimg = document.querySelector(".qdeleteimg");
var imageshowbox = document.querySelector(".imageshowbox");
var upload = document.querySelector("#upload");
var upphotoload = document.querySelector("#upphotoload");
var photoshow = document.querySelector(".photoshow");
var deleteimg = document.querySelector(".deleteimg");
var upphotobox = document.querySelector(".upphotobox");
var thisid = document.querySelector('.thisid');
var reply = document.querySelector('.reply');
var searchshow = document.querySelector(".searchshow");
var backtosearch = document.querySelector(".backtosearch");
var searchcontents = document.querySelector(".searchcontents");
var qdetails_img = document.querySelector(".qdetails_img");
var qimgfile = document.querySelector("#qimgfile");
var main = document.querySelector("#main");
var upphoto = document.querySelector('.upphoto');
var uploadBtn = document.querySelector('.uploadBtn');
var allnew = document.querySelector('.allnew');
var allhot = document.querySelector('.allhot');
var allmyq = document.querySelector('.allmyq');
var allmya = document.querySelector('.allmya');
var nloadmore = document.querySelector('.nloadmore');
var hloadmore = document.querySelector('.hloadmore');
var myqloadmore = document.querySelector('.myqloadmore');
var myaloadmore = document.querySelector('.myaloadmore');

var dataUrl, collegetag, college_tag = 233,
    arr, newquestion, idnum,
    targetid, dataurl, like, likeNum, qformdata, aformdata, orgPic, qthumbPic, athumbPic, action = picPath + 'uploadfile/';
var nick_name = [];
var slip;
var tag = new Array();
var pic_thumb = new Array();
var reply_count = new Array();
var like_count = new Array();
var content = new Array();
var create_time = new Array();
var head_url = new Array();
var id = new Array();
var ansid = new Array();
var like = new Array();
var likeNum = new Array();
var titlee = [];
var npage = 1,
    hpage = 1,
    myqpage = 1,
    myapage = 1;
var w = document.documentElement.clientWidth;
var h = document.documentElement.clientHeight;

var flags = false;


main.style.height = h + 'px';
question_Page.style.height = h + 'px';
answerquestion.style.height = h + 'px';

document.querySelector('.loginbtn').onclick = function() {
    document.querySelector('.loginpage').style.display = 'none';
}

function input_nav(e) {
    e.onfocus = function() {
        search_Page.style.zIndex = '6';
    }

    e.onblur = function() {
        search_Page.style.zIndex = '4';
    }
}

function inputNav(e) {
    e.onfocus = function() {
        question_Page.style.zIndex = '6';
    }

    e.onblur = function() {
        question_Page.style.zIndex = '4';
    }
}

input_nav(searchBox);
inputNav(qwords);
inputNav(qtitle);

document.querySelector('.sendit').onclick = function() {
    var title = document.querySelector('#qtitle').value;
    var content = document.querySelector('#qwords').value;

    if (content.length > 100 || title == '') {
        alert('标题不能为空且内容不能超过一百字')
    } else {
        function upload(q) {
            var xhr = new XMLHttpRequest();
            var url = basePath + 'discuss?type=question';
            xhr.open('POST', url, true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    arr = JSON.parse(xhr.responseText);
                    if (arr.status == 0) {
                        console.log('Got a POST request');
                        headpageshow();
                        questionshow(basePath + 'index');
                        document.querySelector('#qtitle').value = '';
                        document.querySelector('#qwords').value = '';
                        qdelete();
                        qthumbPic = 'undefined';
                    } else {
                        alert(arr.msg)
                    }
                }
            }

            xhr.send(q);
        }
        upload('title=' + title + '&content=' + content + '&orgPic=' + orgPic + '&thumbPic=' + qthumbPic + '&tag=' + collegetag);
    }
}

document.querySelector('.sendanswer').onclick = function() {
    var answerid = thisid.innerHTML;
    var content = document.querySelector('#answerContent').value;
    if (content == '') {
        alert('请输入回复内容')
    } else {
        function upload(q) {
            var xhr = new XMLHttpRequest();
            var url = basePath + 'discuss';
            xhr.open('POST', url, true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    arr = JSON.parse(xhr.responseText);
                    if (arr.status == 0) {
                        console.log('Got a POST request');
                        answerquestion.style.display = "none";
                        replyshow();
                        document.querySelector('#answerContent').value = '';
                        adelete();
                        athumbPic = 'undefined';
                    } else {
                        alert(arr.msg)
                    }

                }
            }

            xhr.send(q);
        }
        console.log('Got answer');
        upload('id=' + answerid + '&type=answer' + '&content=' + content + '&orgPic=' + orgPic + '&thumbPic=' + athumbPic);

    }
}


upload.onchange = function() {
    var formData = new FormData();
    formData.append('upload', upload.files[0]);
    uploadBtn.style.backgroundImage = "url(" + picPath + "imgs/loading.gif)";
    var xhr = new XMLHttpRequest();
    var url = basePath + 'pic_upload.do';
    xhr.open('POST', url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log('Got a POST request');
            arr = JSON.parse(xhr.responseText);
            console.log(arr);
            if (arr.status == 0) {
                qthumbPic = arr.data[0][1];
                orgPic = arr.data[0][0];
                imageshow.src = action + qthumbPic;
                imageshowbox.style.display = 'block';
                qdeleteimg.style.display = "block";
            } else {
                alert(arr.msg)
            }
        }
    }
    xhr.send(formData);
}

upphotoload.onchange = function() {
    var formData = new FormData();
    formData.append('upphotoload', upphotoload.files[0]);
    upphoto.style.backgroundImage = "url(" + picPath + "imgs/loading.gif)";
    var xhr = new XMLHttpRequest();
    var url = basePath + 'pic_upload.do';
    xhr.open('POST', url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log('Got a POST request');
            arr = JSON.parse(xhr.responseText);
            console.log(arr);
            if (arr.status == 0) {
                athumbPic = arr.data[0][1];
                orgPic = arr.data[0][0];
                photoshow.src = action + athumbPic;
                upphotobox.style.display = 'block';
                deleteimg.style.display = "block";
                upphoto.style.backgroundImage = "url(" + picPath + "imgs/camera.png)";
            } else {
                alert(arr.msg)
            }
        }
    }
    xhr.send(formData);
}

function qdelete() {
    qthumpic = undefined;
    imageshowbox.style.display = 'none';
    qdeleteimg.style.display = "none";
    upload.files[0] = '';
    uploadBtn.style.backgroundImage = "url(" + picPath + "imgs/camera.png)";
}

qdeleteimg.onclick = function() {
    qdelete();
}

function adelete() {
    athumbpic = undefined;
    upphotobox.style.display = 'none';
    qdeleteimg.style.display = "none";
    upphotoload.files[0] = '';
    upphoto.style.backgroundImage = "url(" + picPath + "imgs/camera.png)";
}

deleteimg.onclick = function() {
    adelete();
}


window.onload = function() {
    questionshow(basePath + 'index');
};

function gettime(t) {
    var d = new Date(t);
    var now = new Date();
    var day = d.getDate();
    var month = d.getMonth() + 1;
    var year = d.getFullYear();
    var hour = d.getHours();
    var min = d.getMinutes();
    var second = d.getSeconds();
    if (hour < 10) hour = '0' + hour;
    if (min < 10) min = '0' + min;
    if (second < 10) second = '0' + second;
    var date;
    if (now.getFullYear() == year)
        date = month + "月" + day + "日";
    else
        date = year + "年" + month + "月" + day + "日";
    return (date + " " + hour + ":" + min + ":" + second);
}

nloadmore.onclick = function() {
    npage += 1;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'index?page=' + npage, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            if (arr.data.pages >= npage) {
                for (var i = 0, j = (10 * (npage - 1)); i < size, j < (10 * npage); i++, j++) {
                    if (arr.data.list[i].pic_thumb == 'undefined') {
                        pic_thumb[i] = 2335;
                    } else {
                        pic_thumb[i] = arr.data.list[i].pic_thumb;
                    }
                    nick_name[i] = arr.data.list[i].nick_name;
                    if (arr.data.list[i].tag == "undefined") {
                        tag[i] = '';

                    } else {
                        tag[i] = arr.data.list[i].tag;
                    }
                    content[i] = arr.data.list[i].content;
                    titlee[i] = arr.data.list[i].title;
                    reply_count[i] = arr.data.list[i].reply_count;
                    like_count[i] = arr.data.list[i].like_count;
                    id[i] = arr.data.list[i].id;
                    create_time[i] = gettime(arr.data.list[i].create_time);
                    head_url[i] = arr.data.list[i].head_url;
                    var n = document.createElement('div');
                    newquestions.appendChild(n);
                    n.className = 'newquestion';
                    n.innerHTML = '<div class="person">' +
                        '<div class="avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                        '<div class="info">' +
                        '<div class="info_name"><p>' + nick_name[i] + '</p></div>' +
                        '<div class="info_time"><p>' + create_time[i] + '</p></div></div>' +
                        '<div class="info_college"><P>' + tag[i] + '</P></div></div>' +
                        '<div class="questionMiddle">' +
                        '<div class="content"><p>' + titlee[i] + '</p></div>' +
                        '<div class="real_content"><p>' + content[i] + '</p></div>' +
                        '<div class="questionImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                        '<div class="bottom">' +
                        '<div class="comment"><div class="commentIcon"></div>' +
                        '<p class="commentNum">' + reply_count[i] + '</p></div></div>'

                    if (pic_thumb[i] == 2335) {
                        var gg = document.querySelectorAll(".questionImg");
                        gg[j].style.display = 'none';
                    }
                    newquestion = document.querySelectorAll(".newquestion");
                    var d = document.createElement('div');
                    newquestion[j].appendChild(d);
                    d.style.display = 'none';
                    d.innerHTML = id[i];
                }
                toqdetails(newquestion);
            } else {
                if (npage > 0) npage--;
                alert('没有更多数据咯~')
            }
        }
    }
    xhr.send();
}

hloadmore.onclick = function() {
    hpage = hpage + 1;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'index?type=hot&page=' + hpage, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            if (arr.data.pages >= hpage) {
                for (var i = 0, j = (10 * (hpage - 1)); i < size, j < (10 * hpage); i++, j++) {
                    if (arr.data.list[i].pic_thumb == 'undefined') {
                        pic_thumb[i] = 2335;
                    } else {
                        pic_thumb[i] = arr.data.list[i].pic_thumb;
                    }
                    if (arr.data.list[i].tag == "undefined") {
                        tag[i] = '';

                    } else {
                        tag[i] = arr.data.list[i].tag;
                    }
                    nick_name[i] = arr.data.list[i].nick_name;
                    content[i] = arr.data.list[i].content;
                    reply_count[i] = arr.data.list[i].reply_count;
                    like_count[i] = arr.data.list[i].like_count;
                    create_time[i] = arr.data.list[i].create_time;
                    head_url[i] = arr.data.list[i].head_url;
                    titlee[i] = arr.data.list[i].title;
                    id[i] = arr.data.list[i].id;
                    var n = document.createElement('div');
                    hotquestions.appendChild(n);
                    n.className = 'hotquestion';
                    n.innerHTML = '<div class="person">' +
                        '<div class="avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                        '<div class="info">' +
                        '<div class="info_name"><p>' + nick_name[i] + '</p></div>' +
                        '<div class="info_time"><p>' + gettime(create_time[i]) + '</p></div></div>' +
                        '<div class="info_college"><P>' + tag[i] + '</P></div></div>' +
                        '<div class="questionMiddle">' +
                        '<div class="content"><p>' + titlee[i] + '</p></div>' +
                        '<div class="real_content"><p>' + content[i] + '</p></div>' +
                        '<div class="hotquestionImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                        '<div class="bottom">' +
                        '<div class="comment"><div class="commentIcon"></div>' +
                        '<p class="commentNum">' + reply_count[i] + '</p></div></div>'

                    if (pic_thumb[i] == 2335) {
                        var g2 = document.querySelectorAll(".hotquestionImg");
                        g2[j].style.display = 'none';
                    }
                    var hotquestion = document.querySelectorAll(".hotquestion");
                    var d = document.createElement('div');
                    hotquestion[j].appendChild(d);
                    d.style.display = 'none';
                    d.innerHTML = id[i];
                }
                toqdetails(hotquestion);
            } else {
                if (hpage > 0) hpage--;
                alert('没有更多数据咯~')
            }
        }
    }
    xhr.send();
}

function questionshow(url) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    newquestions.innerHTML = ''; //增加
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            if (size < 10) {
                nloadmore.style.display = 'none';
            }
            for (var i = 0; i < size; i++) {
                if (arr.data.list[i].pic_thumb == 'undefined') {
                    pic_thumb[i] = 2333;
                } else {
                    pic_thumb[i] = arr.data.list[i].pic_thumb;
                }
                nick_name[i] = arr.data.list[i].nick_name;
                if (arr.data.list[i].tag == "undefined") {
                    tag[i] = '';

                } else {
                    tag[i] = arr.data.list[i].tag;
                }
                content[i] = arr.data.list[i].content;
                titlee[i] = arr.data.list[i].title;
                reply_count[i] = arr.data.list[i].reply_count;
                like_count[i] = arr.data.list[i].like_count;
                id[i] = arr.data.list[i].id;
                create_time[i] = gettime(arr.data.list[i].create_time);
                head_url[i] = arr.data.list[i].head_url;
                var n = document.createElement('div');
                newquestions.appendChild(n);
                n.className = 'newquestion';
                n.innerHTML = '<div class="person">' +
                    '<div class="avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                    '<div class="info">' +
                    '<div class="info_name"><p>' + nick_name[i] + '</p></div>' +
                    '<div class="info_time"><p>' + create_time[i] + '</p></div></div>' +
                    '<div class="info_college"><P>' + tag[i] + '</P></div></div>' +
                    '<div class="questionMiddle">' +
                    '<div class="content"><p>' + titlee[i] + '</p></div>' +
                    '<div class="real_content"><p>' + content[i] + '</p></div>' +
                    '<div class="questionImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                    '<div class="bottom">' +
                    '<div class="comment"><div class="commentIcon"></div>' +
                    '<p class="commentNum">' + reply_count[i] + '</p></div></div>'

                if (pic_thumb[i] == 2333) {
                    var g = document.querySelectorAll(".questionImg");
                    g[i].style.display = 'none';
                }
                newquestion = document.querySelectorAll(".newquestion");
                var d = document.createElement('div');
                newquestion[i].appendChild(d);
                d.style.display = 'none';
                d.innerHTML = id[i];
            }
            toqdetails(newquestion);
        }
    }
    xhr.send();
}

function myquestionshow() {
    myquestion_contents.innerHTML = ''; //增加
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'mybbm?type=question', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            if (size < 10) {
                myqloadmore.style.display = 'none';
            }
            for (var i = 0; i < size; i++) {
                if (arr.data.list[i].pic_thumb == 'undefined') {
                    pic_thumb[i] = 2334;
                } else {
                    pic_thumb[i] = arr.data.list[i].pic_thumb;
                }
                if (arr.data.list[i].tag == "undefined") {
                    tag[i] = '';

                } else {
                    tag[i] = arr.data.list[i].tag;
                }
                nick_name[i] = arr.data.list[i].nick_name;
                content[i] = arr.data.list[i].content;
                reply_count[i] = arr.data.list[i].reply_count;
                like_count[i] = arr.data.list[i].like_count;
                create_time[i] = arr.data.list[i].create_time;
                head_url[i] = arr.data.list[i].head_url;
                titlee[i] = arr.data.list[i].title;
                id[i] = arr.data.list[i].id;
                var n = document.createElement('div');
                myquestion_contents.appendChild(n);
                n.className = 'myquestion';
                n.innerHTML = '<div class="person">' +
                    '<div class="my_avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                    '<div class="my_info">' +
                    '<div class="myinfo_name"><p>' + nick_name[i] + '</p></div>' +
                    '<div class="myinfo_time"><p>' + gettime(create_time[i]) + '</p></div></div>' +
                    '<div class="myinfo_college"><P>' + tag[i] + '</P></div></div>' +
                    '<div class="questionMiddle">' +
                    '<div class="mycontent"><p>' + titlee[i] + '</p></div>' +
                    '<div class="real_content"><p>' + content[i] + '</p></div>' +
                    '<div class="myquestionImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                    '<div class="bottom">' +
                    '<div class="comment"><div class="commentIcon"></div>' +
                    '<p class="mycommentNum">' + reply_count[i] + '</p></div></div>';
                if (pic_thumb[i] == 2334) {
                    var g1 = document.querySelectorAll(".myquestionImg");
                    g1[i].style.display = 'none';
                }
                var myquestion = document.querySelectorAll(".myquestion");
                var d = document.createElement('div');
                myquestion[i].appendChild(d);
                d.style.display = 'none';
                d.innerHTML = id[i];
            }
            toqdetails(myquestion);
        }
    }
    xhr.send();

}

newq.onclick = function() {
    scrollTo(0, 0);
    allnew.style.display = 'block';
    allhot.style.display = 'none';
    newq.style.backgroundImage = 'url(' + picPath + 'imgs/Chosebg.png)';
    hotq.style.backgroundImage = 'url(' + picPath + 'imgs/noChosebg.png)';
    hotq.style.color = 'black';
    newq.style.color = 'white';
    npage = 1;
    questionshow(basePath + 'index');
}


hotq.onclick = function() {
    scrollTo(0, 0);
    allnew.style.display = 'none';
    allhot.style.display = 'block';
    hotq.style.backgroundImage = 'url(' + picPath + 'imgs/Chosebg.png)';
    newq.style.backgroundImage = 'url(' + picPath + 'imgs/noChosebg.png)';
    newq.style.color = 'black';
    hotq.style.color = 'white';
    hpage = 1;
    hotquestionshow();

}

function hotquestionshow() {
    hotquestions.innerHTML = ''; //增加
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'index?type=hot', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            for (var i = 0; i < size; i++) {
                if (size < 10) {
                    hloadmore.style.display = 'none';
                }
                if (arr.data.list[i].pic_thumb == 'undefined') {
                    pic_thumb[i] = 2335;
                } else {
                    pic_thumb[i] = arr.data.list[i].pic_thumb;
                }
                if (arr.data.list[i].tag == "undefined") {
                    tag[i] = '';

                } else {
                    tag[i] = arr.data.list[i].tag;
                }
                nick_name[i] = arr.data.list[i].nick_name;
                content[i] = arr.data.list[i].content;
                reply_count[i] = arr.data.list[i].reply_count;
                like_count[i] = arr.data.list[i].like_count;
                create_time[i] = arr.data.list[i].create_time;
                head_url[i] = arr.data.list[i].head_url;
                titlee[i] = arr.data.list[i].title;
                id[i] = arr.data.list[i].id;
                var n = document.createElement('div');
                hotquestions.appendChild(n);
                n.className = 'hotquestion';
                n.innerHTML = '<div class="person">' +
                    '<div class="avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                    '<div class="info">' +
                    '<div class="info_name"><p>' + nick_name[i] + '</p></div>' +
                    '<div class="info_time"><p>' + gettime(create_time[i]) + '</p></div></div>' +
                    '<div class="info_college"><P>' + tag[i] + '</P></div></div>' +
                    '<div class="questionMiddle">' +
                    '<div class="content"><p>' + titlee[i] + '</p></div>' +
                    '<div class="real_content"><p>' + content[i] + '</p></div>' +
                    '<div class="hotquestionImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                    '<div class="bottom">' +
                    '<div class="comment"><div class="commentIcon"></div>' +
                    '<p class="commentNum">' + reply_count[i] + '</p></div></div>'

                if (pic_thumb[i] == 2335) {
                    var g2 = document.querySelectorAll(".hotquestionImg");
                    g2[i].style.display = 'none';
                }
                var hotquestion = document.querySelectorAll(".hotquestion");
                var d = document.createElement('div');
                hotquestion[i].appendChild(d);
                d.style.display = 'none';
                d.innerHTML = id[i];
            }
            toqdetails(hotquestion);
        }
    }
    xhr.send();
}

myqloadmore.onclick = function() {
    myqpage = myqpage + 1
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'mybbm?type=question&page=' + myqpage, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            if (arr.data.pages >= myqpage) {
                for (var i = 0, j = (10 * (myqpage - 1)); i < size, j < (10 * myqpage); i++, j++) {

                    if (arr.data.list[i].pic_thumb == 'undefined') {
                        pic_thumb[i] = 2334;
                    } else {
                        pic_thumb[i] = arr.data.list[i].pic_thumb;
                    }
                    if (arr.data.list[i].tag == "undefined") {
                        tag[i] = '';

                    } else {
                        tag[i] = arr.data.list[i].tag;
                    }
                    nick_name[i] = arr.data.list[i].nick_name;
                    content[i] = arr.data.list[i].content;
                    reply_count[i] = arr.data.list[i].reply_count;
                    like_count[i] = arr.data.list[i].like_count;
                    create_time[i] = arr.data.list[i].create_time;
                    head_url[i] = arr.data.list[i].head_url;
                    titlee[i] = arr.data.list[i].title;
                    id[i] = arr.data.list[i].id;
                    var n = document.createElement('div');
                    myquestion_contents.appendChild(n);
                    n.className = 'myquestion';
                    n.innerHTML = '<div class="person">' +
                        '<div class="my_avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                        '<div class="my_info">' +
                        '<div class="myinfo_name"><p>' + nick_name[i] + '</p></div>' +
                        '<div class="myinfo_time"><p>' + gettime(create_time[i]) + '</p></div></div>' +
                        '<div class="myinfo_college"><P>' + tag[i] + '</P></div></div>' +
                        '<div class="questionMiddle">' +
                        '<div class="mycontent"><p>' + titlee[i] + '</p></div>' +
                        '<div class="real_content"><p>' + content[i] + '</p></div>' +
                        '<div class="myquestionImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                        '<div class="bottom">' +
                        '<div class="comment"><div class="commentIcon"></div>' +
                        '<p class="mycommentNum">' + reply_count[i] + '</p></div></div>';
                    if (pic_thumb[i] == 2334) {
                        var g1 = document.querySelectorAll(".myquestionImg");
                        g1[j].style.display = 'none';
                    }
                    var myquestion = document.querySelectorAll(".myquestion");
                    var d = document.createElement('div');
                    myquestion[j].appendChild(d);
                    d.style.display = 'none';
                    d.innerHTML = id[i];
                }
                toqdetails(myquestion);
            } else {
                if (myqpage > 0) myqpage--;
                alert('没有更多数据咯~')
            }
        }
    }

    xhr.send();
}

myaloadmore.onclick = function() {
    myapage = myapage + 1
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'mybbm?type=answer&page=' + myapage, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            if (arr.data.pages >= myapage) {
                for (var i = 0, j = (10 * (myapage - 1)); i < size, j < (10 * myapage); i++, j++) {

                    if (arr.data.list[i].pic_thumb == 'undefined') {
                        pic_thumb[i] = 2336;
                    } else {
                        pic_thumb[i] = arr.data.list[i].pic_thumb;
                    }
                    nick_name[i] = arr.data.list[i].nick_name;
                    if (arr.data.list[i].tag == "undefined") {
                        tag[i] = '';

                    } else {
                        tag[i] = arr.data.list[i].tag;
                    }
                    nick_name[i] = arr.data.list[i].nick_name;
                    content[i] = arr.data.list[i].content;
                    reply_count[i] = arr.data.list[i].reply_count;
                    like_count[i] = arr.data.list[i].like_count;
                    create_time[i] = arr.data.list[i].create_time;
                    head_url[i] = arr.data.list[i].head_url;
                    titlee[i] = arr.data.list[i].title;
                    id[i] = arr.data.list[i].id;
                    var n = document.createElement('div');
                    myanswer_contents.appendChild(n);
                    n.className = 'my_answer';
                    n.innerHTML = '<div class="person">' +
                        '<div class="my_avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                        '<div class="my_info">' +
                        '<div class="myinfo_name"><p>' + nick_name[i] + '</p></div>' +
                        '<div class="myinfo_time"><p>' + gettime(create_time[i]) + '</p></div></div>' +
                        '<div class="myinfo_college"><P>' + tag[i] + '</P></div></div>' +
                        '<div class="questionMiddle">' +
                        '<div class="mycontent"><p>' + titlee[i] + '</p></div>' +
                        '<div class="real_content"><p>' + content[i] + '</p></div>' +
                        '<div class="myanswerImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                        '<div class="bottom">' +
                        '<div class="comment"><div class="commentIcon"></div>' +
                        '<p class="mycommentNum">' + reply_count[i] + '</p></div></div>'

                    if (pic_thumb[i] == 2336) {
                        var g3 = document.querySelectorAll(".myanswerImg");
                        g3[j].style.display = 'none';
                    }
                    var my_answer = document.querySelectorAll(".my_answer");
                    var d = document.createElement('div');
                    my_answer[j].appendChild(d);
                    d.style.display = 'none';
                    d.innerHTML = id[i];
                }
                toqdetails(my_answer);
            } else {
                if (myapage > 0) myapage--;
                alert('没有更多数据咯~')
            }
        }
    }
    xhr.send();
}

myq.onclick = function() {
    allmyq.style.display = 'block';
    allmya.style.display = 'none';
    myq.style.backgroundImage = 'url(' + picPath + 'imgs/Chosebg.png)';
    myanswer.style.backgroundImage = 'url(' + picPath + 'imgs/noChosebg.png)';
    myanswer.style.color = 'black';
    myq.style.color = 'white';
    myqpage = 1;
    myquestionshow();
}

myanswer.onclick = function() {
    myapage = 1;
    allmyq.style.display = 'none';
    allmya.style.display = 'block';
    myanswer.style.backgroundImage = 'url(' + picPath + 'imgs/Chosebg.png)';
    myq.style.backgroundImage = 'url(' + picPath + 'imgs/noChosebg.png)';
    myq.style.color = 'black';
    myanswer.style.color = 'white';
    myanswer_contents.innerHTML = ''; //增加
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'mybbm?type=answer', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.size;
            if (size < 10) {
                console.log(size);
                myaloadmore.style.display = 'none';
            }
            for (var i = 0; i < size; i++) {
                if (arr.data.list[i].pic_thumb == 'undefined') {
                    pic_thumb[i] = 2336;
                } else {
                    pic_thumb[i] = arr.data.list[i].pic_thumb;
                }
                nick_name[i] = arr.data.list[i].nick_name;
                if (arr.data.list[i].tag == "undefined") {
                    tag[i] = '';

                } else {
                    tag[i] = arr.data.list[i].tag;
                }
                nick_name[i] = arr.data.list[i].nick_name;
                content[i] = arr.data.list[i].content;
                reply_count[i] = arr.data.list[i].reply_count;
                like_count[i] = arr.data.list[i].like_count;
                create_time[i] = arr.data.list[i].create_time;
                head_url[i] = arr.data.list[i].head_url;
                titlee[i] = arr.data.list[i].title;
                id[i] = arr.data.list[i].id;
                var n = document.createElement('div');
                myanswer_contents.appendChild(n);
                n.className = 'my_answer';
                n.innerHTML = '<div class="person">' +
                    '<div class="my_avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                    '<div class="my_info">' +
                    '<div class="myinfo_name"><p>' + nick_name[i] + '</p></div>' +
                    '<div class="myinfo_time"><p>' + gettime(create_time[i]) + '</p></div></div>' +
                    '<div class="myinfo_college"><P>' + tag[i] + '</P></div></div>' +
                    '<div class="questionMiddle">' +
                    '<div class="mycontent"><p>' + titlee[i] + '</p></div>' +
                    '<div class="real_content"><p>' + content[i] + '</p></div>' +
                    '<div class="myanswerImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                    '<div class="bottom">' +
                    '<div class="comment"><div class="commentIcon"></div>' +
                    '<p class="mycommentNum">' + reply_count[i] + '</p></div></div>'

                if (pic_thumb[i] == 2336) {
                    var g3 = document.querySelectorAll(".myanswerImg");
                    g3[i].style.display = 'none';
                }
                var my_answer = document.querySelectorAll(".my_answer");
                var d = document.createElement('div');
                my_answer[i].appendChild(d);
                d.style.display = 'none';
                d.innerHTML = id[i];
            }
            toqdetails(my_answer);
        }
    }
    xhr.send();
}



function toqdetails(arr) {
    for (let i = 0; i < arr.length; i++) {
        arr[i].addEventListener("click", function(e) {
            slip = window.scrollY;
            targetid = this.children[3].innerHTML;
            thisid.innerHTML = targetid;
            if (e.target.className != "likeitIcon" && e.target.className != "likeNum" && e.target.className != "likeIcon") {
                qdetails.style.display = "block";
                qdetails_name.innerHTML = this.children[0].children[1].children[0].innerHTML;
                qdetails_college.innerHTML = this.children[0].children[2].children[0].innerHTML;
                qcommentNum.innerHTML = this.children[2].children[0].children[1].innerHTML;
                qdetails_title.innerHTML = this.children[1].children[0].innerHTML;
                qdetails_contents.innerHTML = this.children[1].children[1].innerHTML;
                var q_src = this.children[1].children[2].children[0].src;
                document.querySelector('.qdetailshead').src = this.children[0].children[0].children[0].src; //增加详情页头像
                if (q_src == action + '2333' || q_src == action + '2334' || q_src == action + '2335' || q_src == action + '2336') {
                    qdetails_img.style.display = 'none'
                } else {
                    qdetails_img.style.display = 'block'
                    qdetails_img.src = q_src;
                }
                replyshow();
                var qdetails_imgs = document.querySelectorAll('.qdetails_imgs');
                if (qdetails_imgs != 'undefined') {
                    imgtobig(qdetails_imgs);
                }
            }
        });
    }
}

function replyshow() {
    reply.innerHTML = '';
    var xhr = new XMLHttpRequest();
    var url1 = basePath + 'discuss' + '?id=' + targetid;
    xhr.open("GET", url1, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            var size = arr.data.reply_count;
            for (var i = 0; i < size; i++) {
                if (arr.data.repliesList[i].pic_thumb == 'undefined') {
                    pic_thumb[i] = 2337;
                } else {
                    pic_thumb[i] = arr.data.repliesList[i].pic_thumb;
                }
                if (arr.data.repliesList[i].tag == "undefined") {
                    tag[i] = '';
                } else {
                    tag[i] = arr.data.repliesList[i].tag;
                }
                nick_name[i] = arr.data.repliesList[i].nick_name;
                content[i] = arr.data.repliesList[i].content;
                like_count[i] = arr.data.repliesList[i].like_count;
                create_time[i] = arr.data.repliesList[i].create_time;
                head_url[i] = arr.data.repliesList[i].head_url;
                ansid[i] = arr.data.repliesList[i].id;
                var n = document.createElement('div');
                reply.appendChild(n);
                n.className = 'replyContents';
                n.innerHTML = '<div class="reply_avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                    '<div class="reply_content">' +
                    '<div class="reply_top">' +
                    '<p class="reply_name">' + nick_name[i] + '</p>' +
                    '<img class="xueba" src="' + picPath + 'imgs/xueba.png">' +
                    '<div class="took" ><div class="notake"></div></div>' +
                    '</div><div class="replyWords">' +
                    '<p>' + content[i] + '</p></div>' +
                    '<div class="reply_Img">' +
                    '<img class="qdetails_imgs  imgtoreply" src="' + action + pic_thumb[i] + '"></div>' +
                    '<div class="reply_bottom">' +
                    '<p class="datetime">' + gettime(create_time[i]) + '</p>' +
                    '<div class="reply_likeit like">' +
                    '<div class="likeIcon"></div>' +
                    '<p class="likeNum">' + like_count[i] + '</p></div></div></div>'

                if (pic_thumb[i] == 2337) {
                    var r = document.querySelectorAll(".imgtoreply"); //更换类名
                    r[i].style.display = 'none';
                }
                var qdetails_imgs = document.querySelectorAll('.qdetails_imgs');
                if (arr.data.repliesList[i].scholar) {
                    var xueba = document.querySelectorAll('.xueba');
                    xueba[i].style.display = 'inline-block';
                }
                var took = document.querySelectorAll('.took');
                if (arr.data.repliesList[i].accepted) {
                    console.log(1);
                    took[i].children[0].className = 'take';
                }
                var like4 = document.querySelectorAll('.like');
                if (arr.data.repliesList[i].upvote) {
                    like4[i].children[0].className = 'likeitIcon';
                }
                var reply_content = document.querySelectorAll(".reply_content");
                var d = document.createElement('div');
                reply_content[i].appendChild(d);
                d.style.display = 'none';
                d.innerHTML = ansid[i];
                took[i].onclick = function() {
                    var takeid = this.parentNode.parentNode.lastChild.innerHTML;
                    if (arr.data.mine) {
                        if (this.children[0].className == 'notake') {
                            this.children[0].className = 'take';
                            var xhr = new XMLHttpRequest();
                            xhr.open('GET', basePath + 'discuss/operation?id=' + takeid + '&type=accept', true);
                            xhr.onreadystatechange = function() {
                                if (xhr.readyState == 4 && xhr.status == 200) {
                                    console.log('send accepted');
                                }
                            }
                            xhr.send();
                        } else {
                            this.children[0].className = 'notake';
                            var xhr = new XMLHttpRequest();
                            xhr.open('GET', basePath + 'discuss/operation?id=' + takeid + '&type=not_accept', true);
                            xhr.onreadystatechange = function() {
                                if (xhr.readyState == 4 && xhr.status == 200) {
                                    console.log('not_accept');
                                }
                            }
                            xhr.send();
                        }
                    }
                }
                var like4 = document.querySelectorAll('.like');
                if (qdetails_imgs != 'undefined') {
                    imgtobig(qdetails_imgs);
                }
                if (like4 != 'undefined') {
                    like4[i].addEventListener("click", function() {
                        likeid = this.parentNode.parentNode.lastChild.innerHTML;
                        if (this.children[0].className == 'likeIcon') {
                            this.children[0].className = 'likeitIcon';
                            this.children[1].innerHTML = parseInt(this.children[1].innerHTML) + 1;

                            var xhr = new XMLHttpRequest();
                            xhr.open('GET', basePath + 'discuss/operation?id=' + likeid + '&type=like', true);
                            xhr.onreadystatechange = function() {
                                if (xhr.readyState == 4 && xhr.status == 200) {
                                    console.log('send like');
                                }
                            }
                            xhr.send();
                        } else {
                            this.children[0].className = 'likeIcon';
                            this.children[1].innerHTML = parseInt(this.children[1].innerHTML) - 1;
                            var xhr = new XMLHttpRequest();
                            xhr.open('GET', basePath + 'discuss/operation?id=' + likeid + '&type=unlike', true);
                            xhr.onreadystatechange = function() {
                                if (xhr.readyState == 4 && xhr.status == 200) {
                                    console.log('send unlike');
                                }
                            }
                            xhr.send();
                        }
                    });
                }
            }
        }


    }
    xhr.send();
}

back.onclick = function() {
    qdetails.style.display = "none";
    nav.style.display = "block";
}

function imgtobig(m) {
    for (let i = 0; i < m.length; i++) {
        m[i].onclick = function() {
            console.log(m);
            // biggerimg.style.backgroundImage = "url(" + m[i].src + ")";
            biggerimg.src = m[i].src;
            var biggerimgbox = document.querySelector(".biggerimgbox");
            biggerimgbox.style.width = '80vw';
            biggerimgbox.style.height = ((0.8 * w) / biggerimg.width) * biggerimg.height + 'px';
            var imgh = ((0.8 * w) / biggerimg.width) * biggerimg.height;
            biggerimgbox.style.top = (h - imgh) * 0.5 + 'px';
            biggerimgbox.style.left = (w - (0.8 * w)) * 0.5 + 'px';
            biggerimgs.style.display = 'block';
        }
    }
}

mask.onclick = function() {
    biggerimgs.style.display = 'none';
}



function headpageshow() {
    headpage.style.backgroundImage = "url(" + picPath + "imgs/onheadPage.png)";
    qPage.style.backgroundImage = "url(" + picPath + "imgs/qPage.png)";
    sPage.style.backgroundImage = "url(" + picPath + "imgs/sPage.png)";
    myPage.style.backgroundImage = "url(" + picPath + "imgs/myPage.png)";
    contents.style.display = "block";
    question_Page.style.display = "none";
    search_Page.style.display = "none";
    my_page.style.display = "none";
}


headpage.onclick = function() {
    headpageshow();
}

sPage.onclick = function() {
    searchcontents.innerHTML = '';
    headpage.style.backgroundImage = "url(" + picPath + "imgs/headPage.png)";
    qPage.style.backgroundImage = "url(" + picPath + "imgs/qPage.png)";
    sPage.style.backgroundImage = "url(" + picPath + "imgs/onsPage.png)";
    myPage.style.backgroundImage = "url(" + picPath + "imgs/myPage.png)";
    contents.style.display = "none";
    question_Page.style.display = "none";
    search_Page.style.display = "block";
    my_page.style.display = "none";


    document.querySelector("#searchBox").onsearch = function() {
        document.querySelector("#searchBox").blur();
        var key = document.querySelector("#searchBox").value;
        console.log(key);
        console.log(college_tag);
        searchcontents.innerHTML = '';
        var xhr = new XMLHttpRequest();
        if (college_tag == 233) {
            url2 = basePath + 'search?key=' + key
        } else {
            url2 = basePath + 'search?key=' + key + '&tag=' + college_tag
        }
        xhr.open("GET", url2, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                arr = JSON.parse(xhr.responseText);
                console.log(arr);
                var size = arr.data.size;
                searchshow.style.display = 'block';
                for (var i = 0; i < size; i++) {
                    if (arr.data.list[i].pic_thumb == 'undefined') {
                        pic_thumb[i] = 2333;
                    } else {
                        pic_thumb[i] = arr.data.list[i].pic_thumb;
                    }
                    nick_name[i] = arr.data.list[i].nick_name;
                    if (arr.data.list[i].tag == "undefined") {
                        tag[i] = '';

                    } else {
                        tag[i] = arr.data.list[i].tag;
                    }
                    nick_name[i] = arr.data.list[i].nick_name;
                    content[i] = arr.data.list[i].content;
                    reply_count[i] = arr.data.list[i].reply_count;
                    like_count[i] = arr.data.list[i].like_count;
                    create_time[i] = arr.data.list[i].create_time;
                    head_url[i] = arr.data.list[i].head_url;
                    titlee[i] = arr.data.list[i].title;
                    id[i] = arr.data.list[i].id;
                    var n = document.createElement('div');
                    searchcontents.appendChild(n);
                    n.className = 'searchcontent';
                    console.log(n);
                    n.innerHTML = '<div class="person">' +
                        '<div class="avatar"><img class="myhead" src="' + head_url[i] + '"></div>' +
                        '<div class="info">' +
                        '<div class="info_name"><p>' + nick_name[i] + '</p></div>' +
                        '<div class="info_time"><p>' + gettime(create_time[i]) + '</p></div></div>' +
                        '<div class="info_college"><P>' + tag[i] + '</P></div></div>' +
                        '<div class="questionMiddle">' +
                        '<div class="content"><p>' + titlee[i] + '</p></div>' +
                        '<div class="real_content"><p>' + content[i] + '</p></div>' +
                        '<div class="squestionImg"><img src="' + action + pic_thumb[i] + '"></div></div>' +
                        '<div class="bottom">' +
                        '<div class="comment"><div class="commentIcon"></div>' +
                        '<p class="commentNum">' + reply_count[i] + '</p></div></div>'

                    if (pic_thumb[i] == 2333) {
                        var g8 = document.querySelectorAll(".squestionImg");

                        g8[i].style.display = 'none';
                    }
                    var searchcontent = document.querySelectorAll(".searchcontent");
                    var d = document.createElement('div');
                    searchcontent[i].appendChild(d);
                    d.style.display = 'none';
                    d.innerHTML = id[i];
                }
                toqdetails(searchcontent);
            }
        }
        xhr.send();
    }
}

qPage.onclick = function() {
    headpage.style.backgroundImage = "url(" + picPath + "imgs/headPage.png)";
    qPage.style.backgroundImage = "url(" + picPath + "imgs/onqPage.png)";
    sPage.style.backgroundImage = "url(" + picPath + "imgs/sPage.png)";
    myPage.style.backgroundImage = "url(" + picPath + "imgs/myPage.png)";
    contents.style.display = "none";
    question_Page.style.display = "block";
    search_Page.style.display = "none";
    my_page.style.display = "none";

}

myPage.onclick = function() {
    headpage.style.backgroundImage = "url(" + picPath + "imgs/headPage.png)";
    qPage.style.backgroundImage = "url(" + picPath + "imgs/qPage.png)";
    sPage.style.backgroundImage = "url(" + picPath + "imgs/sPage.png)";
    myPage.style.backgroundImage = "url(" + picPath + "imgs/onmyPage.png)";
    contents.style.display = "none";
    question_Page.style.display = "none";
    search_Page.style.display = "none";
    my_page.style.display = "block";

    infoshow();
    myquestionshow();
}

function infoshow() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", basePath + 'user', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            arr = JSON.parse(xhr.responseText);
            console.log(arr);
            var username = arr.data.nick_name;

            document.querySelector('.myId').innerHTML = username;
            var headimg = arr.data.head_url;
            document.querySelector('.myHead').src = headimg;
        }
    }
    xhr.send();
}

for (let i = 0; i < choseCollege.length; i++) {
    choseCollege[i].onclick = function(event) {
        for (let i = 0; i < choseCollege.length; i++) {
            choseCollege[i].style.backgroundColor = "white";
            choseCollege[i].style.color = '#b8b8b8';
        }
        event.target.style.backgroundColor = "#ffd510";
        event.target.style.color = 'white';
        collegetag = event.target.innerHTML;

    }
}

for (let i = 0; i < searchCollege.length; i++) {
    searchCollege[i].onclick = function() {
        for (let i = 0; i < choseCollege.length; i++) {
            searchCollege[i].style.backgroundColor = "#f7f7f7";
            searchCollege[i].style.color = '#b8b8b8';
        }
        event.target.style.backgroundColor = "#ffd510";
        event.target.style.color = 'white';
        college_tag = event.target.innerHTML;
    }
}

answerquestion.style.height = h;
replyImg.onclick = function() {
    answerquestion.style.display = "block";
}


backto.onclick = function() {
    answerquestion.style.display = "none";
}

backtosearch.onclick = function() {
    searchshow.style.display = 'none';
}