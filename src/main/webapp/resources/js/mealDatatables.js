var ajaxUrl = "ajax/profile/meals/";
var datatableApi;
var dateTime;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (datetime, type, row) {
                    if (type === 'display') {
                        return '<span>' + datetime.substring(0, 10) + ' ' + datetime.substring(11, 19) + '</span>';
                    }
                    return datetime;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.exceed) {
                $(row).addClass('exceeded');
            } else {
                $(row).addClass('normal');
            }
        },
        "initComplete": makeEditable
    });
    $('#dateTime').datetimepicker({
        format: 'Y-m-d\\TH:i',
        lang: 'ru'
    });
});