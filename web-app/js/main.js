/**
 * Created with IntelliJ IDEA.
 * User: Joseph Lee <josel@pdx.edu>
 * Date: 7/17/13
 * Time: 10:22 PM
 */

function uploadButtonClick() {
    var uploadFormElems = $('#upload-form .upload-form-elem');
    var submit = $('#upload-form input[type=submit]');
    var addSeq = $('#upload-form input[type=button].add-seq');

    // check length of sequence list
    if (uploadFormElems.length > 0) {
        submit.removeAttr('disabled');
        if (uploadFormElems.length >= 2) {
            addSeq.attr('disabled', 'disabled');
        }
        else {
            addSeq.removeAttr('disabled');
        }
    }
    else {
        submit.attr('disabled', 'disabled');
    }
}

(function($) {
    var typeaheadOptions = {
        source: function(query, process) {
            var that = this;
            console.log(process);
            var results = $.get('/webapp/_search_name',
                { query: query },
                function(data, status, jqXHR) {
                    console.log(data);
                    console.log(that);
                    process(data.results);
                });
        },
        items: 20
    };
    /*
    // Show modal by default if no query string
    var qmarkIndex = window.location.href.indexOf('?');
    if (qmarkIndex < 0 || qmarkIndex == window.location.href.length - 1) {
        $('#upload').modal('show');
    }*/
    // show upload modal on click
    $('#upload-nav').click(function(event) {
        $('#upload').modal('show');
    });

    // Upload menu settings
    $('input[type=button].add-seq#upload').on('click', function(event) {
        var i = $('.upload-form-elem').length;
        var newSeqElem = $('<div class="upload-form-elem">' +
            '<div class="control-group">' +
            '<label class="control-label" for="userSequenceFile' + i + '">Sequence File</label>' +
        '<div class="controls"><input type="File" id="userSequenceFile' + i +
            '" name="userSequenceFile' + i +
            '" required style="background-color: rgba(0, 0, 0, 0); border: 0;"/></div>' +
        '</div>' +
        '<div class="control-group">' +
            '<label class="control-label" for="userOrganism' + i +
            '">Organism Name</label>' +
            '<div class="controls"><input type="text" name="userOrganism' + i +
            '" id="userOrganism' + i + '" placeholder="Enter scientific name" required/></div>' +
        '</div><hr>' +
        '</div>').hide();
        $('#upload-form-list').append(newSeqElem);
        newSeqElem.fadeIn();
        uploadButtonClick();
    });
    // Upload menu settings
    $('input[type=button].add-seq#genbank').on('click', function(event) {
        var i = $('.upload-form-elem').length;
        var newSeqElem = $('<div class="upload-form-elem">' +
            '<div class="control-group">' +
            '<label class="control-label" for="genbankOrganism' + i + '">Scientific Name</label>' +
            '<div class="controls"><input type="text" name="genbankOrganism' + i +
            '" class="search-query" id="genbankOrganism' + i +
            '" placeholder="Enter scientific name" autocomplete="off" required/></div>' +
            '</div><hr>' +
            '</div>').hide();
        $('#upload-form-list').append(newSeqElem);
        newSeqElem.fadeIn();
        uploadButtonClick();
        $('#upload-form .search-query').typeahead($.extend(typeaheadOptions, { items: 24 }));
        // TODO: Change to jquery-UI's scrollable autocompletion
    });

    // Autocompletion for organism name search text
    $('.search-query').typeahead(typeaheadOptions);
})(jQuery);