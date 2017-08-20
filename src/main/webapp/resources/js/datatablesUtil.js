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

    $('#reset').trigger('reset');

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
            if (ajaxUrl == 'ajax/admin/users/') updateTable();
            else filter();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
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
            if (ajaxUrl == 'ajax/admin/users/') updateTable();
            else filter();
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
        success: function (data) {
            datatableApi.clear();
            $.each(data, function (key, item) {
                datatableApi.row.add(item);
            });
            datatableApi.draw();
            successNoty('Filtered');
        }
    });
}

function toggle(checkbox) {
    var id = $(checkbox).parent().parent().attr("id");
    var enabled = checkbox.checked;
    $.ajax({
        url: ajaxUrl + id + '/' + enabled,
        type: 'POST',
        success: function () {
            updateTable();
            successNoty('User status changed');
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
