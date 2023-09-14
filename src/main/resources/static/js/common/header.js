// 더보기 마이페이지 넣기
$(document).ready(function(){

});
//약식
$(function(){

});
$(function(){
    $(".sub_menu > ul > li").mouseover(function(){
        $(this).find(".submenu").stop().slideDown();
    });
    $(".sub_menu > ul > li").mouseout(function(){
        $(this).find(".submenu").stop().slideUp();
    });
})