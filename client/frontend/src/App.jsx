import { Route, Routes } from "react-router-dom";
import Header from "./componnets/header/Header";
import Login from "./componnets/login/Login";


function App() {
 

  return (
   <>

       <Header />
       <h1>is Work</h1>
       <Routes>
         <Route path="/login" element={<Login />} />

       </Routes>
      
   </>
  );
}

export default App;
