export const convertStrToDate = function(str) {
    var date = new Date(str),
        mnth = ("0" + (date.getMonth() + 1)).slice(-2),
        day = ("0" + date.getDate()).slice(-2),
        hour = ("0" + date.getHours()).slice(-2),
        min = ("0" + date.getMinutes()).slice(-2);
    return [date.getFullYear(), mnth, day + 'T' + hour + ':' + min].join("-");
};

export const prepareChartData = function(map) {
    var result = [];
    map.forEach(item => {
        let event = { name: item.eventName, uv: item.personCount }
        result.push(event);
    })
    return result;
}

export const prepareSecChartData = function(map) {
    var result = [];
    map.forEach(item => {
        let event = { name: item[0], uv: item[1] }
        result.push(event);
    })
    return result;
}

export const isAttendingEventNow = function(event) {
    let currentDate = Date.now()
    let startDate = Date.parse(event.value.startDate)
    let endDate = Date.parse(event.value.endDate)
    if (event.value.isAttended && currentDate > startDate && currentDate < endDate) {
        return true;
    }
    return false;
}

export const castValuesToIntegerOfJSONObject = function(obj) {
    for (var key in obj) {
        obj[key] = parseInt(obj[key]);
    }
    return obj
}

export const temperatureConversion = function(fah) {
    return ((parseFloat(fah) - 32) / 1.8).toFixed(1);
}

export const getSurveyChartData = function(dataArray) {
    let resultArray = [{
            "subject": "Genel Memnuniyet",
            "A": dataArray[0],
            "fullMark": 10
        },
        {
            "subject": "İçerik",
            "A": dataArray[1],
            "fullMark": 10
        },
        {
            "subject": "Etkinlik Konumu",
            "A": dataArray[2],
            "fullMark": 10
        },
        {
            "subject": "Organizasyon",
            "A": dataArray[3],
            "fullMark": 10
        },
        {
            "subject": "Başvuru Kolaylığı",
            "A": dataArray[4],
            "fullMark": 10
        },
        {
            "subject": "Tekrar Katılma",
            "A": dataArray[5],
            "fullMark": 10
        },
        {
            "subject": "Başkalarına Önerme",
            "A": dataArray[6],
            "fullMark": 10
        }
    ]
    return resultArray;
}