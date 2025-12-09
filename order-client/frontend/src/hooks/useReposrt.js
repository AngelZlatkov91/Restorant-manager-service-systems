import { useEffect, useState } from "react";
import reportApi from "../api/repost-api";

export function useGetReport() {
    const [report,setReport] = useState([]);
    const fetchReport = async () => {
        try {
            const data = await reportApi.report();
            setReport(data);
        } catch (err) {
            console.log(err.message);
        }
    };
    useEffect(() => {
        fetchReport();
    },[]);
    

    return [report, fetchReport];

}