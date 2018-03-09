/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//loader 
$(document).ajaxStart(function () {
//    alert("1")
    $('.modalLoader').show();
});
$(document).ajaxStop(function () {
//    alert("2")
    $('.modalLoader').hide();
});


function peticionAjax(ctr, datos) {

    $.ajax({
        url: ctr,
        type: "POST",
        data: datos,
        dataType: "text",
        success: function (data) {
            eval(data);
        },
        error: function () {

            alert("Error en el procesamiento");
        }
    });

}


function peticionAjaxConfirm(ctr, datos, msj) {
    bootbox.confirm({
        message: msj,
        buttons: {
            confirm: {
                label: 'Si',
                className: 'btn-success'
            },
            cancel: {
                label: 'No',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {

                $.ajax({
                    url: ctr,
                    type: "POST",
                    data: datos,
                    dataType: "text",
                    success: function (data) {

                        eval(data);
                    },
                    error: function () {

                        alert("Error en el procesamiento");
                    }
                });
            }
        }
    });
}
function alerta(title, text) {
    $.gritter.add({
        title: title,
        text: text,
//			image: 'assets/img/user-3.jpg',
        sticky: false,
        time: '3000'
    });
    return false;
}

function cerrarModal(script) {
    console.log(script)
    $(".close").click();
    setTimeout(function () {
        eval(script);

    }, '1000');
    $('.modal').removeClass('show');
    $('.modal-backdrop').removeClass('modal-backdrop');
}


function RecargaPanel(Url, Panel, callback) {

    if (callback === undefined) {
        $("#" + Panel).load(Url);
    } else {
        $("#" + Panel).load(Url, function () {
            eval(callback);
        });

    }
}
function cadenaAleatoria(length)
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < length; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}
function encodeImageFileAsURL() {

    var filesSelected = document.getElementById("firma_input").files;
    if (filesSelected.length > 0) {
        var fileToLoad = filesSelected[0];

        var fileReader = new FileReader();

        fileReader.onload = function (fileLoadedEvent) {
            var srcData = fileLoadedEvent.target.result; // <--- data: base64

            $("#firma_txt").val(srcData);
            var newImage = document.createElement('img');
            newImage.src = srcData;
            newImage.width = '200';

            document.getElementById("img_firma").innerHTML = newImage.outerHTML;
        }
        fileReader.readAsDataURL(fileToLoad);
    }
}


var activeModals = [];
function modalDialog(title, url, data, size) {
    var next = activeModals.length;
//     console.log(next);
    var dialog;
    $.ajax({
        url: url, // url a cargar en el dialogo
        data: data, // datos necesarios para esa url
        cache: false
    })
            .done(function (html) {
                activeModals[next] = bootbox.dialog({
                    message: html,
                    title: title, // titulo del dialogo
                    size: size === undefined ? "large" : size, // tamaÃ±o del dialogo (large, small, null)
                    onEscape: true
                });
            });
}

function closeModal() {
    setTimeout(function () {
        var x = $(document).find(' div.bootbox.modal.fade.in');
        $(x[x.length - 1]).modal("hide");
    }, 1000);
}
function closeModalAll() {
    bootbox.hideAll();
}
