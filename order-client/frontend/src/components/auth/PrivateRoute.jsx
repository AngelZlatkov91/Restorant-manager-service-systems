import { useState, useEffect } from "react";
import { Navigate } from "react-router-dom";

export default function PrivateRoute({ children }) {
  const [allowed, setAllowed] = useState(null);

  useEffect(() => {
    window.electronAPI.getToken().then((token) => {
      setAllowed(!!token);
    });
  }, []);

  if (allowed === null) return null; // loader, празно

  if (!allowed) return <Navigate to="/loginPage" replace />;

  return children;
}
