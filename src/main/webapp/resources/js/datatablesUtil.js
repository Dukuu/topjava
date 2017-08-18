function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).parent().parent().attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#filter').click(function () {
        filter();
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable(ajaxUrl);
            successNoty('Deleted');
        }
    });
}

function updateTable(url) {
    $.get(url, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable(ajaxUrl);
            successNoty('Saved');
        }
    });
}

function filter() {
    var form = $('#filterform');
    $.ajax({
        type: 'POST',
        url: 'ajax/user/meals/filter/',
        data: form.serialize(),
        success: function () {
            updateTable('ajax/user/meals/filter/');
            successNoty('Filtered');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 1500
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
