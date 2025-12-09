import { useGetReport } from "../../hooks/useReposrt";

export default function Reports() {
  const [report, fetchReport] = useGetReport();
    console.log(report);
   return (
       <>
          <h1>Reports</h1>

          <table>
            <thead>
              <tr>
                <th>Personal Name</th>
                <th>Total Cost Sum</th>
              </tr>
            </thead>
            <tbody>
              {report.map((rep, index) => (
                <tr key={index}>
                  <td>{rep.personalName}</td>
                  <td>{rep.totalCostSum}</td>
                  <td><button>Reporting</button></td>
                </tr>
              ))}
              
            </tbody>
          </table>
       
       </>
  );
}