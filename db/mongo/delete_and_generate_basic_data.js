

//db.athlete.find()

use('racedb');
deleteData();
generateData();

async function deleteData(){
 
    await db.race.drop();
    await db.athlete.drop();
    await db.application.drop();
  

}

async function generateData(){    

let athlete_applications = 30000;
let athlete_capacity = 8000;
let concurrent_request_treshold = 20;
let race_id=0;
let result;

let race = {
    "name": "Test race",
    "description": "Test Race Marathon",
    "date": "2023-02-01 09:00:00",
    "location": "Madrid",
    "distance": 42.195,
    "type": "Running",
    "athleteCapacity": athlete_capacity ,
    "applicationInitialDate": "2022-01-01T00:00:00",
    "applicationLastDate": "2022-12-31T23:59:00",
    "organizerName": "Test Organizer",
    "raceRegistrationDate": "2023-01-01T09:00:00",
    "registrationType": "BYORDER",
    "registrationCost": 50.0
};


result = await db.race.insertOne(race);
race_id = result.insertedId;

//race_id = "6370da98bc4dcb717418bdb3"

  //for (let counter=1; counter<=athlete_applications; counter++)
  for (let counter=1; counter<=2; counter++)
  {
    let athlete_id;
    let application_id;

    let athlete;
    let application;

    athlete = {
        "name": "Test ahtlete " + counter,
        "surname": "Test " + counter
    };

    result = await db.athlete.insertOne(athlete);


   athlete_id = result.insertedId;

  

    application = {
        "athleteId": athlete_id,
        "raceId": race_id,
        "applicationCode": getApplicationCode(10)
    }
   result = await db.application.insertOne(application);

}
}

function getApplicationCode(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}