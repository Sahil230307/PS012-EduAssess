let token=localStorage.getItem("eduassess_token");
let role=localStorage.getItem("eduassess_role");
let activeSubmission=null;

const $=id=>document.getElementById(id);
function authHeaders(){return {"Content-Type":"application/json","Authorization":"Bearer "+token}}
async function api(path, options={}){
  options.headers={...(options.headers||{}),...authHeaders()};
  const r=await fetch(path,options);
  const text=await r.text(); let data={};
  try{data=text?JSON.parse(text):{}}catch{data={raw:text}};
  if(!r.ok) throw new Error(data.error||data.message||data.raw||("HTTP "+r.status));
  return data;
}
function boot(){
 if(token){$("authCard").hidden=true;$("app").hidden=false;$("userInfo").textContent="Role: "+role;
   if(role==="STUDENT"){$("studentPanel").hidden=false;loadExams()}
   if(role==="ADMIN"){$("adminPanel").hidden=false;loadAdminExams()}
 }
}
async function login(){
 try{
  const d=await fetch("/auth/login",{method:"POST",headers:{"Content-Type":"application/json"},
    body:JSON.stringify({email:$("email").value,password:$("password").value})}).then(async r=>{
      const x=await r.json();if(!r.ok)throw new Error(x.error||"Login failed");return x;
    });
  token=d.token;role=d.role;localStorage.setItem("eduassess_token",token);localStorage.setItem("eduassess_role",role);boot();
 }catch(e){$("authMsg").textContent=e.message}
}
function logout(){localStorage.clear();location.reload()}
async function loadExams(){
 try{const exams=await api("/exams");$("examList").innerHTML=exams.map(e=>`
  <div class="exam"><b>${e.title}</b><br>${e.description||""}<br>Duration: ${e.durationMinutes} min | Start: ${new Date(e.startTime).toLocaleString()}
  <br><button onclick="startExam(${e.id})">Start Exam</button></div>`).join("")||"<p>No published exams.</p>"
 }catch(e){$("examList").textContent=e.message}
}
async function startExam(id){
 try{
  activeSubmission=await api(`/submissions/exams/${id}/start`,{method:"POST",body:"{}"});
  const qs=await api(`/exams/${id}/questions`);
  $("examArea").innerHTML=`<div class="card"><h2>Exam #${id}</h2><p>Deadline: ${new Date(activeSubmission.deadline).toLocaleString()}</p>
  <form id="examForm">${qs.map((q,i)=>`<div class="question"><b>${i+1}. ${q.questionText}</b>
   ${["A","B","C","D"].map(o=>`<label><input type="radio" name="q${q.id}" value="${o}"> ${o}. ${q["option"+o]}</label>`).join("<br>")}</div>`).join("")}
  <button type="button" onclick="submitExam(${id})">Submit Exam</button></form></div>`
 }catch(e){alert(e.message)}
}
async function submitExam(id){
 const answers=[];
 document.querySelectorAll(".question").forEach(q=>{
   const input=q.querySelector("input:checked"); if(input) answers.push({questionId:Number(input.name.substring(1)),selectedOption:input.value});
 });
 try{const result=await api(`/submissions/${activeSubmission.submissionId}/submit`,{method:"POST",body:JSON.stringify({answers})});
  $("examArea").innerHTML=`<div class="card"><h2>Result</h2><h3>${result.score} / ${result.totalMarks}</h3><p>Percentage: ${Number(result.percentage).toFixed(2)}%</p><p>Status: ${result.status}</p></div>`;
 }catch(e){alert(e.message)}
}
async function loadAdminExams(){
 try{const exams=await api("/exams/all");$("adminExamList").innerHTML=exams.map(e=>`<div class="exam">#${e.id} <b>${e.title}</b> — ${e.status}
 ${e.status==="DRAFT"?`<button onclick="publishExam(${e.id})">Publish</button>`:""} </div>`).join("")}
 catch(e){$("adminExamList").textContent=e.message}
}
async function createExam(){
 try{
  let start=$("examStart").value?new Date($("examStart").value).toISOString():new Date().toISOString();
  const e=await api("/exams",{method:"POST",body:JSON.stringify({title:$("examTitle").value,description:$("examDescription").value,
    durationMinutes:Number($("examDuration").value),startTime:start})});
  $("questionExamId").value=e.id;$("adminMsg").textContent="Created exam #"+e.id;loadAdminExams();
 }catch(e){$("adminMsg").textContent=e.message}
}
async function publishExam(id){try{await api(`/exams/${id}/publish`,{method:"POST",body:"{}"});loadAdminExams()}catch(e){alert(e.message)}}
async function addQuestion(){
 try{const id=$("questionExamId").value;const q=await api(`/exams/${id}/questions`,{method:"POST",body:JSON.stringify({
  questionText:$("qText").value,optionA:$("optA").value,optionB:$("optB").value,optionC:$("optC").value,optionD:$("optD").value,
  correctOption:$("qCorrect").value.toUpperCase(),marks:Number($("qMarks").value)})});$("adminMsg").textContent="Question added: "+q.id
 }catch(e){$("adminMsg").textContent=e.message}
}
async function monitorResults(){
 try{const id=$("monitorExamId").value;const [subs,res]=await Promise.all([api(`/submissions/exam/${id}`),api(`/evaluations/exam/${id}`)]);
  $("results").textContent=JSON.stringify({submissions:subs,results:res},null,2)
 }catch(e){$("results").textContent=e.message}
}
boot();
