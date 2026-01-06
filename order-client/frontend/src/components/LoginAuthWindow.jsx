const CLICK_SOUND_BASE64 =
  "data:audio/wav;base64,UklGRiQAAABXQVZFZm10IBAAAAABAAEAQB8AAEAfAAABAAgAZGF0YQAAAAA=";

import { useState, useEffect } from "react";
import { useLogin } from "../hooks/useAuth";

const PIN_LENGTH = 4;

export default function LoginAuthWindow() {
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [shake, setShake] = useState(false);
  const [darkMode, setDarkMode] = useState(true);

  const login = useLogin();

  // ======================
  // Sound
  // ======================
  const playClick = () => {
  const audio = new Audio(CLICK_SOUND_BASE64);
  audio.volume = 0.4;
  audio.play().catch(() => {});
};

  // ======================
  // Auto submit
  // ======================
  useEffect(() => {
    if (password.length === PIN_LENGTH) {
      submit();
    }
  }, [password]);

  const submit = async () => {
    const result = await login(password);

    if (result?.name) {
      window.electronAPI.loginSuccess(result);
    } else {
      setError("Грешна парола");
      setPassword("");
      setShake(true);
      setTimeout(() => setShake(false), 400);
    }
  };

  // ======================
  // Keypad logic
  // ======================
  const addDigit = (d) => {
    if (password.length < PIN_LENGTH) {
      playClick();
      setPassword((p) => p + d);
    }
  };

  const backspace = () => {
    playClick();
    setPassword((p) => p.slice(0, -1));
  };

  const clear = () => {
    playClick();
    setPassword("");
  };

  return (
    <div style={styles.wrapper(darkMode)}>
      <div style={styles.card}>
        {/* HEADER */}
        <div style={styles.header}>
          <h2>Вход</h2>
          <button onClick={() => setDarkMode(!darkMode)} style={styles.modeBtn}>
            {darkMode ? "🌙" : "☀️"}
          </button>
        </div>

        {/* PIN DOTS */}
        <div
          style={{
            ...styles.pinBox,
            ...(shake ? styles.shake : {}),
          }}
        >
          {[...Array(PIN_LENGTH)].map((_, i) => (
            <span key={i} style={styles.dot}>
              {password[i] ? "●" : "○"}
            </span>
          ))}
        </div>

        {error && <p style={styles.error}>{error}</p>}

        {/* KEYPAD */}
        <div style={styles.keypad}>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((n) => (
            <Key key={n} onClick={() => addDigit(n)}>
              {n}
            </Key>
          ))}

          <Key onClick={clear}>C</Key>
          <Key onClick={() => addDigit(0)}>0</Key>
          <Key onClick={backspace}>⌫</Key>
        </div>
      </div>
    </div>
  );
}

// ======================
// Key component
// ======================
function Key({ children, onClick }) {
  return (
    <button onClick={onClick} style={styles.key}>
      {children}
    </button>
  );
}

// ======================
// Styles
// ======================
const styles = {
  wrapper: (dark) => ({
    minHeight: "100vh",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    background: dark ? "#121212" : "#f5f5f5",
    fontFamily: "Arial, sans-serif",
  }),

  card: {
    background: "#1e1e1e",
    padding: 40,
    borderRadius: 16,
    boxShadow: "0 10px 30px rgba(0,0,0,0.4)",
    color: "#fff",
    width: 380,
  },

  header: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: 20,
  },

  modeBtn: {
    background: "transparent",
    border: "none",
    fontSize: 20,
    cursor: "pointer",
    color: "#fff",
  },

  pinBox: {
    display: "flex",
    justifyContent: "center",
    gap: 12,
    fontSize: 28,
    marginBottom: 15,
  },

  dot: {
    width: 20,
    textAlign: "center",
  },

  keypad: {
    display: "grid",
    gridTemplateColumns: "repeat(3, 1fr)",
    gap: 14,
    marginTop: 20,
  },

  key: {
    height: 70,
    fontSize: 24,
    borderRadius: 14,
    border: "none",
    background: "#2f2f2f",
    color: "#fff",
    cursor: "pointer",
    userSelect: "none",
    touchAction: "manipulation",
  },

  error: {
    textAlign: "center",
    color: "#ff5c5c",
    margin: 0,
  },

  shake: {
    animation: "shake 0.4s",
  },
};
