import React from "react";

export default function ConfirmPopup({ message, onConfirm, onCancel, isOpen, position }) {
  if (!isOpen) return null;


  const style = {
    position: "absolute",
    top: position?.top || "50%",
    left: position?.left || "50%",
    transform: "translate(-50%, -50%)",
    background: "white",
    border: "1px solid #ccc",
    borderRadius: "8px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.2)",
    padding: "16px",
    zIndex: 1000,
    width: "280px",
  };

  const backdropStyle = {
    position: "fixed",
    inset: 0,
    zIndex: 900,
    background: "transparent", 
  };

  return (
    <>
      <div style={backdropStyle} onClick={onCancel} />
      <div style={style}>
        <p style={{ marginBottom: "12px" }}>{message}</p>
        <div style={{ display: "flex", justifyContent: "flex-end", gap: "8px" }}>
          <button
            onClick={onCancel}
            style={{
              padding: "6px 12px",
              borderRadius: "4px",
              border: "1px solid #aaa",
              background: "#f0f0f0",
              cursor: "pointer",
            }}
          >
            Cancel
          </button>
          <button
            onClick={onConfirm}
            style={{
              padding: "6px 12px",
              borderRadius: "4px",
              border: "none",
              background: "#f44336",
              color: "white",
              cursor: "pointer",
            }}
          >
            Confirm
          </button>
        </div>
      </div>
    </>
  );
}
