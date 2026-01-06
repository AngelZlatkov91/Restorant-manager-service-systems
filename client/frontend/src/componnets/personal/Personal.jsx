import { useNavigate } from "react-router-dom";
import { useGetAllPersonal } from "../../hooks/usePersonal";


export default function Personal() {
    const navigate = useNavigate();
    const [personals,fetchPersonal] = useGetAllPersonal();



  

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
                       key={personal.id} 
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
                         <strong>Name: {personal.name}</strong>
                         <p style={{ margin: "5px 0" }}>
                           Password: {personal.password} 
                         </p>
                         <strong>ROLE: {personal.role}</strong>
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
         
         
                       </div>
                     </li>
                   ))}
                 </ul>
               ) : (
                 <p>Няма налични персонали.</p>
               )}

             </section>
  );
}