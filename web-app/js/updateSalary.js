function updateSalary() {
    var data = jQuery('#workprofile').serialize();
    jQuery.ajax({type:'POST', url:'/salary-calculator/calculator/updateEarnings/', data: data, success:function(data, textStatus) {
        jQuery('#earnings').html(data);
    },error:function(XMLHttpRequest, textStatus, errorThrown) {
    }});
    return false;
}

$(document).ready(function() {
    setInterval('updateSalary()', 5000);
})