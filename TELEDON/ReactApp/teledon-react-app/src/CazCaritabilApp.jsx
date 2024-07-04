import {useEffect, useState} from "react";
import {AddCazCaritabil, DeleteCazCaritabil, GetCazuriCaritabile, UpdateCazCaritabil} from "./utils/rest-calls.js";
import CazCaritabilTable from "./CazCaritabilTable.jsx";

export default function CazCaritabilApp()
{
    const [cazuriCaritabile, setCazuriCaritabile] = useState([]);


    function addFunc(cazCaritabil) {
        console.log('inside add Func ' + cazCaritabil);
        AddCazCaritabil(cazCaritabil)
            .then(()=> GetCazuriCaritabile()) // Corrected to invoke GetCazuriCaritabile function
            .then(cazuriCaritabile => setCazuriCaritabile(cazuriCaritabile))
            .catch(error => console.log('eroare add ', error));
    }

    function deleteFunc(cazCaritabil)
    {
        console.log('inside delete Func '+ cazCaritabil);
        DeleteCazCaritabil(cazCaritabil)
            .then(() => GetCazuriCaritabile())
            .then(cazuriCaritabile => setCazuriCaritabile(cazuriCaritabile))
            .catch(error => console.log('roare delete ', error));
    }

    function updateFunc(cazCaritabil)
    {
        console.log('inside update Func '+cazCaritabil);
        UpdateCazCaritabil(cazCaritabil)
            .then(() => GetCazuriCaritabile())
            .then(cazuriCaritabile => setCazuriCaritabile(cazuriCaritabile))
            .catch(err => console.log('eroare update', err));
    }

    useEffect(()=>{
        console.log('inside useEffect')
        GetCazuriCaritabile()
            .then(cazuriCaritabile => setCazuriCaritabile(cazuriCaritabile))
            .catch(error => console.log('fetch error: ', error))
        ;}
        ,[]);



    return (<div className="CazCaritablApp">
        <h1> Caz Caritabil </h1>
        <CazCaritabilTable cazuriCaritabileList={cazuriCaritabile} addFunc={addFunc} deleteFunc={deleteFunc} updateFunc={updateFunc}/>
    </div>);

}