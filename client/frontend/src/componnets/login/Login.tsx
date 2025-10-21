import React, { useState } from 'react';

interface LoginProps {
  onLogin: (username: string, password: string) => void;
}

const Login: React.FC<LoginProps> = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!username || !password) {
      setError('Please enter username and password');
      return;
    }

    setError('');
    onLogin(username, password);
  };

  return (
   <>
          <section id="login-page" className="auth">
            <form id="login" onSubmit={handleSubmit}>

                <div className="container">
                    <div className="brand-logo"></div>
                    <h1>Login</h1>
                    
                    <label htmlFor="username">Email:</label>
                    <input 
                    type="text" 
                    id="username" 
                    name="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Sokka@gmail.com" 
                    />

                    <label htmlFor="login-pass">Password:</label>
                    <input 
                    type="password" 
                    id="login-password" 
                    name="password"
                    value={password}
                    onChange={(e) => setUsername(e.target.value)}
                    />
                    
                    <input type="submit" className="btn submit" value="Login" />
                    <p className="field">
                        <span>If you don't have profile click <a href="#">here</a></span>
                    </p>
                </div>
            </form>
        </section>
       </>
  );
};

export default Login;



