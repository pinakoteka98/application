
var today = new Date();
var curYear = today.getFullYear().toString();
var curMonth = (today.getMonth() + 1.).toString();
var curDay = today.getDate().toString();

var month = formatDate(curMonth);
var day = formatDate(curDay);

var monthString = curYear + "-" + month;
var dayString = curYear + "-" + month + "-" + day;

function formatDate(num){
    if (num.length < 2){
        return '0' + num;
    } else {
        return num;
    }
}

$(document).ready( function () {
    var table = $('#apptTable').DataTable({
        "sAjaxSource": "/services/all",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "firstname" },
            { "mData": "lastname" },
            { "mData": "appointmentDate" },
            { "mData": "appointmentTime" }
        ],
        "oSearch" : {"sSearch": dayString}
    });

    $('#clear-filter').on('click', function () {
        table.search('').draw();
    });

    $('#filter-day').on('click', function () {
        table.search(dayString).draw();
    });

    $('#filter-month').on('click', function () {
        table.search(monthString).draw();
    });
    $('#filter-year').on('click', function () {
        table.search(curYear).draw();
    });
});
