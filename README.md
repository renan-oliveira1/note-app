# Note App
Note app é um aplicativo com a finalidade de gerenciar anotações, sendo possivel criar, editar e excluir as anotações do usuario.

# Tecnologias usadas:
- ROOM
- LiveData
- ViewBinding
- Material Design
- ListAdapter
- MVVM
- Dagger Hilt

# Pacotes da aplicação

-> data: Onde se localiza os subpacotes que tangem a aplicação principal, responáveis pela parte instanciada do sistema</br>
&nbsp; - data_source: implementação do consumo e criação do banco de dados</br>
&nbsp; - repository: Pacote responsável por implementar o contrato definido pelo módulo domain na Repository (Interface).</br>
-> domain: Contém o dominio da aplicação. Desde entidades até usecases que serão usados no sistema</br>
&nbsp; - entities: Responsável por encapsular as entidades do sistema que serão usadas durante as instancias</br>
&nbsp; - usecases: Dita o comportamento do sistema e a interação entre as classes e DAOs</br>
&nbsp; - repository: Interface que define o contrato a ser feito pelos repositories  do módulo data</br>
-> presentation: Contém apresentação dos dados, animações, listas e execução das Classes e Objetos Android criadas</br>
