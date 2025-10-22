import { Route, Routes } from "react-router-dom";
import Header from "./componnets/header/Header";
import Login from "./componnets/login/Login";
import Register from "./componnets/register/Register";
import Menu from "./componnets/menu/Menu";


function App() {
 

  return (
   <>

       <Header />
       <h1>is Work</h1>
       <Routes>
         <Route path="/login" element={<Login />} />
         <Route path="/register" element={<Register />} />
         <Route path="/menu" element={<Menu />} />
       </Routes>
      
   </>
  );
}

export default App;
