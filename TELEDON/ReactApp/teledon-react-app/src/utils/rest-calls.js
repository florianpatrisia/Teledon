import {CAZURI_CARITABILE_BASE_URL}  from "./consts.js";


function status(response)
{
    console.log('response status '+ response.status);
    if(response.status>=200 && response.status < 300)
    {
        return Promise.resolve(response)
    }
    else
    {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response)
{
    return response.json()
}


export function GetCazuriCaritabile()
{
    let headers=new Headers();
    headers.append('Accept', 'application/json');
    let myInit={
        method: 'GET',
        headers: headers,
        mode: 'cors'
    };

    let request= new Request(CAZURI_CARITABILE_BASE_URL, myInit);

    console.log('Inainte de fetch GET pentru '+ CAZURI_CARITABILE_BASE_URL)

    return fetch(request)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}


export function DeleteCazCaritabil(id)
{
    console.log('Inainte de fetch delete')
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    let antet = { method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'
    };

    const cazDelUrl=CAZURI_CARITABILE_BASE_URL+'/'+id;
    console.log('URL pentru delete   '+ cazDelUrl)
    return fetch(cazDelUrl,antet)
        .then(status)
        .then(response=>{
            console.log('Delete status '+response.status);
            return response.text();
        }).catch(e=>{
            console.log('error '+e);
            return Promise.reject(e);
        });

}


export function AddCazCaritabil(cazCaritabil)
{
    console.log('Inainte de fetch post'+JSON.stringify(cazCaritabil));

    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    let antet = { method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(cazCaritabil)};

    console.log('URL pentru adaugare   '+ CAZURI_CARITABILE_BASE_URL)
    return fetch(CAZURI_CARITABILE_BASE_URL,antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}


export function UpdateCazCaritabil(cazCaritabil)
{
    console.log('Inainte de fetch put'+JSON.stringify(cazCaritabil));

    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    let antet = { method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(cazCaritabil)};

    let update_url=CAZURI_CARITABILE_BASE_URL+'/'+cazCaritabil.id;

    console.log('URL pentru update   '+update_url)
    return fetch(update_url,antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}





