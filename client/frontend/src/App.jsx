import { useEffect, useState } from "react";

function App() {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    if (window.electronAPI) {
      window.electronAPI.onMessage("fromMain", (data) => {
        setMessages((prev) => [...prev, data]);
      });
    }
  }, []);

  const sendMessage = () => {
    window.electronAPI?.sendMessage("toMain", "Hello from React!");
  };

  return (
    <div style={{ padding: 20 }}>
      <h1>Electron + React</h1>
      <button onClick={sendMessage}>Send Message to Electron</button>
      <ul>
        {messages.map((msg, i) => (
          <li key={i}>{msg}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;
