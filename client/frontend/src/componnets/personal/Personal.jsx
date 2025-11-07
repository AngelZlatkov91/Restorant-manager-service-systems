import { useNavigate } from "react-router-dom";
import { useDeletePersonal, useGetAllPersonal } from "../../hooks/usePersonal";
import ConfirmPopup from "../confirmModal/ConfirmPop";
import { useState } from "react";

export default function Personal() {
    const navigate = useNavigate();
    const [personals,fetchPersonal] = useGetAllPersonal();
    const [isConfirmOpen, setIsConfirmOpen] = useState(null);
    const [personalToDelete, setPersonalToDelete] = useState(null);
    const [popupPos, setPopupPos] = useState({ top: 0, left: 0});

    const handleDeleteClick = (e,personal) => {
         const rect = e.target.getBoundingClientRect();
         setPopupPos({ top: rect.top + window.scrollY + 30, left: rect.left + rect.width / 2 });
         setPersonalToDelete(personal);
         setIsConfirmOpen(true);
    };

    const handleConfirm = async () => {
      if (!personalToDelete) return;
      await useDeletePersonal(personalToDelete.id);
      setIsConfirmOpen(false);
      setPersonalToDelete(null);
    };

    const handleCancel = () => {
      setIsConfirmOpen(false);
      setPersonalToDelete(null);
    };

    const createPersonalHandler = () => {
      navigate('/createPersonal');
    }
    

    const handleEdit = (id) => {
      navigate(`/editPersonal/${id}`);
    };

   return (
         <section id="items" style={{ padding: "20px" }}>
               <h2>Персонал</h2>
               <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={createPersonalHandler} style={{
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "5px",
                  background: "#4caf50",
                  color: "white",
                  cursor: "pointer",
                }}>Create Personal</button>
              </div>
               {personals && personals.length > 0 ? (
                 <ul style={{ listStyle: "none", padding: 0 }}>
                   {personals.map((personal) => (
                     <li
                       key={personal.id} // ✅ уникален key
                       style={{
                         display: "flex",
                         justifyContent: "space-between",
                         alignItems: "center",
                         background: "#f9f9f9",
                         marginBottom: "10px",
                         padding: "10px 15px",
                         borderRadius: "8px",
                         boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
                       }}
                     >
                       <div>
                         <strong>Име: {personal.name}</strong>
                         <p style={{ margin: "5px 0" }}>
                           Password: {personal.password} 
                         </p>
                       </div>
         
                       <div style={{ display: "flex", gap: "10px" }}>
                         <button
                           onClick={() => handleEdit(personal.id)}
                           style={{
                             padding: "6px 12px",
                             border: "none",
                             borderRadius: "5px",
                             background: "#4caf50",
                             color: "white",
                             cursor: "pointer",
                           }}
                         >
                           Edit
                         </button>
         
                         <button
                           onClick={(e) => handleDeleteClick(e,personal)}
                           style={{
                             padding: "6px 12px",
                             border: "none",
                             borderRadius: "5px",
                             background: "#f44336",
                             color: "white",
                             cursor: "pointer",
                           }}
                         >
                           Delete
                         </button>
         
                       </div>
                     </li>
                   ))}
                 </ul>
               ) : (
                 <p>Няма налични артикули.</p>
               )}
         
               <ConfirmPopup
                   isOpen={isConfirmOpen}
                    message={`Сигурен ли си, че искаш да изтриеш "${personalToDelete?.name}"?`}
                   onConfirm={handleConfirm}
                   onCancel={handleCancel}
                  position={{ top: "50%", left: "50%" }}
                 />
             </section>
  );
}