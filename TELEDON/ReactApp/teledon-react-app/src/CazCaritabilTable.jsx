import React, {useState} from 'react';
import CazCaritabilApp from "./CazCaritabilApp.jsx";

import {createRoot} from "react-dom/client";


function ResultRow({cazCaritabil, deleteFunc, updateFunc})
{
    const [updatedNume, setUpdatedNume]=useState(cazCaritabil.nume);
    const [updatedDscriere, setUpdatedDescriere]=useState(cazCaritabil.descriere);
    const [updatedSumaStransa, setUpdatedSumaStransa]=useState(cazCaritabil.sumaStransa);
    const [updatedSumaFinala, setUpdatedSumaFinala]=useState(cazCaritabil.sumaFinala);
    const [isModified, setIsModified] = useState(false);

    function handleDelete()
    {
        console.log('Delete button pentru '+ cazCaritabil.id);
        deleteFunc(cazCaritabil.id);
    }

    function handleUpdate()
    {
     console.log("Update: "+ updatedNume);
     cazCaritabil.nume=updatedNume;
     cazCaritabil.descriere=updatedDscriere;
     cazCaritabil.sumaStransa=updatedSumaStransa;
     cazCaritabil.sumaFinala=updatedSumaFinala;
     console.log('Update button pentru '+ cazCaritabil.id);
     updateFunc(cazCaritabil);
    }

    const handleEdit = () =>
    {
        setIsModified(true);
    };

    const handleCancel = () =>
    {
        setIsModified(false);
        setUpdatedNume(cazCaritabil.nume);
        setUpdatedDescriere(cazCaritabil.descriere);
        setUpdatedSumaStransa(cazCaritabil.sumaStransa);
        setUpdatedSumaFinala(cazCaritabil.sumaFinala);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        handleUpdate();
        setIsModified(false);
    };

    return(
        <tr>
            <td>
                {isModified ? (
                    <input
                        type="text"
                        value={updatedNume}
                        onChange={(e) => setUpdatedNume(e.target.value)}
                    />
                ) : (
                    cazCaritabil.nume
                )}
            </td>


            <td>
                {isModified ? (
                    <input
                        type="text"
                        value={updatedDscriere}
                        onChange={(e) => setUpdatedDescriere(e.target.value)}
                    />
                ) : (
                    cazCaritabil.descriere
                )}
            </td>


            <td>
                {isModified ? (
                    <input
                        type="number"
                        value={updatedSumaStransa}
                        onChange={(e) => setUpdatedSumaStransa(e.target.value)}
                    />
                ) : (
                    cazCaritabil.sumaStransa
                )}
            </td>

            <td>
                {isModified ? (
                    <input
                        type="number"
                        value={updatedSumaFinala}
                        onChange={(e) => setUpdatedSumaFinala(e.target.value)}
                    />
                ) : (
                    cazCaritabil.sumaFinala
                )}
            </td>

            <td>
                {isModified ? (
                    <>
                        <button onClick={handleSubmit}>Save</button>
                        <button onClick={handleCancel}>Cancel</button>
                    </>
                ) : (
                    <>
                        <button onClick={handleDelete}>Delete</button>
                        <button onClick={handleEdit}>Update</button>

                    </>
                )}
            </td>

        </tr>
    )
}

function NewResultRow({addFunction, setIsAdding }) {
    const [newNume, setNewNume] = useState('');
    const [newDescriere, setNewDescriere] = useState('');
    const [newSumaStransa, setNewSumaStransa] = useState(0);
    const [newSumaFinala, setNewSumaFinala] = useState(0);

    const handleAdd = () => {
        const newEvent = {
            nume: newNume,
            descriere: newDescriere,
            sumaStransa: newSumaStransa,
            sumaFinala: newSumaFinala
        };
        addFunction(newEvent);
        setIsAdding(false);
    };

    const handleCancel = () => {
        setIsAdding(false);
    };

    return (
        <tr>
            <td>
                <input type="text" value={newNume} onChange={(e) => setNewNume(e.target.value)} />
            </td>
            <td>
                <input type="text" value={newDescriere} onChange={(e) => setNewDescriere(e.target.value)} />
            </td>
            <td>
                <input type="number" value={newSumaStransa} onChange={(e) => setNewSumaStransa(parseFloat(e.target.value))} />
            </td>
            <td>
                <input type="number" value={newSumaFinala} onChange={(e) => setNewSumaFinala(parseFloat(e.target.value))} />
            </td>
            <td>
                <button onClick={handleAdd}>Save</button>
                <button onClick={handleCancel}>Cancel</button>
            </td>
        </tr>
    );
}

export default function CazCaritabilTable({cazuriCaritabileList, addFunc, deleteFunc, updateFunc}) {
    console.log("In Caz Caritabil Table");
    console.log(cazuriCaritabileList);

    const [isAdding, setIsAdding] = useState(false);

    let rows = [];

    cazuriCaritabileList.forEach(function (cazCaritabil) {
        console.log("CAZ CARITABIL " + cazCaritabil);
        rows.push(<ResultRow cazCaritabil={cazCaritabil} deleteFunc={deleteFunc} updateFunc={updateFunc} />)
        });

    return (
        <div className="CazCaritabilTable">

            <table className="center">
                <thead>
                <tr>
                    {/*<th>Id</th>*/}
                    <th>Nume</th>
                    <th>Descriere</th>
                    <th>Suma Stransa</th>
                    <th>Suma Finala</th>
                    <th>Actiuni</th>
                </tr>
                </thead>

                <tbody>
                    {rows}
                    {isAdding && <NewResultRow addFunction={addFunc} setIsAdding={setIsAdding}/>}
                </tbody>
            </table>

            <button onClick={() => setIsAdding(true)}>Add Event</button>
        </div>
    );
}
