import { useNavigate, useParams } from "react-router-dom";
import { useGetAllMenuItems } from "../../hooks/useItem";

export default function NotEmptyTable() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [items,fetchItems] = useGetAllMenuItems();
    console.log(items);
    const getAllTable = () => {
        navigate('/tables');
    }
    return (
        <>
            <h1>Not Empty</h1>
            <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={getAllTable} style={{
                    padding: "6px 12px",
                    border: "none",
                    borderRadius: "5px",
                    background: "#4caf50",
                    color: "white",
                    cursor: "pointer",
                }}>ALL TABLES</button>
            </div>
            

        </>
    );
}