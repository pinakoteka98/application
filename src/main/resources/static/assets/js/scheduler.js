$(document).ready(function () {
    let initialDate = moment().add(1, 'd').format("YYYY-MM-DD");
    $('#date').val(initialDate);
    $('#firstname').val($('#tempFirst').val());
    $('#lastname').val($('#tempLast').val());
    $.getJSON("/services/all/" + initialDate, function (data) {
        let responseData = data;
        console.log(responseData.length);
        if (responseData.length < 1){
            let allowableTimes = ["11:00:00", "13:00:00", "15:00:00"];
            for (let time = 0; time < allowableTimes.length; time++){
                $('#appts').append('<option value=' + allowableTimes[time] + '>' + formatOptions(allowableTimes[time]) + '</option>');
            }
        } else {
            let allowableTimes = ["11:00:00", "13:00:00", "15:00:00"];
            for (let i = 0; i < responseData.length; i++){
                let apptTime = responseData[i].appointmentTime;
                let index = allowableTimes.indexOf(apptTime);
                if (index !== -1){
                    allowableTimes.splice(index, 1);
                }
            }
            for (let x = 0; x < allowableTimes.length; x++){

                $('#appts').append('<option value=' + allowableTimes[x] + '>' + formatOptions(allowableTimes[x]) + '</option>');
            }
            console.log("Times Taken -- " + responseData);
            console.log("Available Times -- " + allowableTimes);
        }
    });
    $('#date').change(function () {
        let selectList = document.getElementById('appts');
        while (selectList.firstChild){
            selectList.removeChild(selectList.firstChild);
        }
        let newDate = document.getElementById('date').value;
        $.getJSON("/services/all/" + newDate, function (data) {

            let responseData = data;
            console.log(responseData.length);
            if (responseData.length < 1){
                let allowableTimes = ["11:00:00", "13:00:00", "15:00:00"];
                for (let time = 0; time < allowableTimes.length; time++){
                    $('#appts').append('<option value=' + allowableTimes[time] + '>' + formatOptions(allowableTimes[time]) + '</option>');
                }
            } else {
                let allowableTimes = ["11:00:00", "13:00:00", "15:00:00"];
                for (let i = 0; i < responseData.length; i++){
                    let apptTime = responseData[i].appointmentTime;
                    let index = allowableTimes.indexOf(apptTime);
                    if (index !== -1){
                        allowableTimes.splice(index, 1);
                    }
                }
                for (let x = 0; x < allowableTimes.length; x++){

                    $('#appts').append('<option value=' + allowableTimes[x] + '>' + formatOptions(allowableTimes[x]) + '</option>');
                }
                console.log("Times Taken -- " + responseData);
                console.log("Available Times -- " + allowableTimes);
            }
        });
    });

    $('#apptForm').submit(function () {
        let fakeDate = $('#date').val();
        let realDate = moment(fakeDate).format("YYYY-MM-DD");
        $('#realDate').val(realDate);
    })
});

function formatOptions(timeString) {
    let returnString = "";
    timeString = timeString.replace(new RegExp(":", "g"), '');
    timeString = timeString.replace(new RegExp("[0]{4}$", "g"), '');
    // if(timeString === "8" || timeString === "9" || timeString === "10" || timeString === "11"){
    //     returnString = timeString + " AM";
    // } else {
    //     returnString = timeString + " PM";
    // }
    return timeString;
}