function updateSalary() {
    var salary = jQuery('input[name=annualSalary]').val();
    jQuery.ajax({type:'POST', url:'/salary-calculator/calculator/updateEarnings/' + salary, success:function(data, textStatus) {
        jQuery('#earnings').html(data);
    },error:function(XMLHttpRequest, textStatus, errorThrown) {
    }});
    return false;
}

$(document).ready(function() {
    setInterval('updateSalary()', 5000);
})