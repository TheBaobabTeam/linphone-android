from docx import Document
from docx.shared import Inches

document = Document()

document.add_heading('Unit Testing Results Document', 0)

table = document.add_table(rows=5, cols=4)

p_name = table.cell(0,0).text = 'Project Name: '


document.save('Acceptance_Test_Results.docx')

