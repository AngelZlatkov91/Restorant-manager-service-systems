import { useEffect, useState } from "react";

import personalApi from "../api/personal-api";


export function useCreatePersonal() {
    const personal = (data) => personalApi.createPersonal(data);
    return personal;
}

export function useGetAllPersonal() {
    const [personals, setPersonals] = useState([]);
    const fetchPersonal = async () => {
        const result = await personalApi.getAllPersonal();
        setPersonals(result);
    };

    useEffect(() => {
        fetchPersonal();
    },[]);

    return [personals,fetchPersonal]
}

export function useChangePersonalStatus() {
    const changeStatus = async (id) => {
        const result = await personalApi.changePersonalStatus(id);
        return result;
    };

    return changeStatus;
}

export function useGetPersonalById(id) {
    const [personal, setPersonal] = useState({
        id: '',
        name: '',
        password: '',
        role: '',
        active: Boolean,
    });
    useEffect(() => {
        (async () => {
            try {
                 const result =await personalApi.getPersonalById(id);
                 setPersonal(result);
            } catch (err) {

            }
            })();
    },[id])


    return [personal, setPersonal];
}


