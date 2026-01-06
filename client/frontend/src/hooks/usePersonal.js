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


