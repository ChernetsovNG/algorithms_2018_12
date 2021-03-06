**Решения домашних заданий по курсу "Алгоритмы для разработчиков" (группа от декабря 2018 года) на Otus**

**Условия заданий:**

**1) Реализация алгоритма сортировки на RAM (Random Access Machine)**

   Запрограммировать алгоритм простой сортировки на RAM:
   
   для (каждого i от 0 до N-1)
   
   для (каждого k от i+1 до N)
   
   если(элемент[k] < элемент[i]) {
   
   поменять местами элемент[k] и элемент[i]
     
     элемент_tmp = элемент[k];
     
     элемент[k] = элемент[i];
     
     элемент[i] = элемент_tmp;
   
   }
      
   Опционально (но очень желательно):
   
   Доделать улучшение в алгоритм умножения через сложение
   
   Доделать алгоритм деления с остатком через вычитание
   
**2) Реализация очереди с приоритетом**

   Написать реализацию PQueue - очередь с приоритетом.
   
   Варианты реализации - список списков, массив списков
   
   Методы к реализации:
   
   enqueue(int priority, T item) - поместить элемент в очередь
   
   T dequeue() - выбрать элемент из очереди
   
   Для реализации можно использовать только стандартный массив [], 
   массив или список из материалов к занятию. Стандартные библиотеки не используем!
   
   Опционально (но очень желательно):
   
   Доделать алгоритм IArray - массив массивов с неполным заполнением. 
   Делать на основе BArray - блочный массив вашей реализации, на основе приложенного к материалам DArray
   А еще лучше сделать список массивов. Сами блоки (строки) это массивы, а вертикально они объединяются списком.
   Потому что что бы понять, в какой блок мы попали, все равно бежать перебором.
   Тоже брать только массив BArray и OList нашей реализации.
   
   реализовать метод T get(int i) - взять элемент по индексу
   
   Совсем опционально:
   
   add(int index, T item) - добавить элемент в произвольное место массива
   remove(index) - удалить произвольный элемент массива
   
**3) Вычисление чисел Фибоначчи**

   Написать реализацию вычисления чисел Фибоначчи через матричный алгоритм
   
   Опционально:
   
   Алгоритм решета Эратосфена, экономичный к памяти, сразу откинуть четные числа
   Варианты: битовые операции, сегментация
   
   1) Битовые операции - храним как элемент массива целое число, например byte в Java 8 бита. Соответственно, каждый бит представляет собой true/false для определенного числа. Используя этот алгоритм можно уменьшить потребности в памяти в 8 раз. Откинув четные числа, еще в 2 раза.
   
   2) Сегментация - делаем блоками по определенного размера. Выделяем блок фиксированного размера считаем в нем, например блок размером 1000, а посчитать надо до 5000. Соответственно сеем 5 блоков, не забывая, как у нас смещаются индексы.

**4) Реализовать алгоритмы Insertion Sort и Shell Sort**

   Дополнительно: 
   
   протестировать работу алгоритмов на случайных массивах и на практически упорядоченных массивах, сравнить производительность. Сравнить производительнсоть сортировки Шелла c разной последовательностью шагов.

**5) Реализация пирамидальной сортировки (heapsort)**

**6) Реализовать quicksort или mergesort**

**7) Реализовать сортировку подсчетом, поразрядную сортировку, блочную сортировку (bucket sort)** 

**8) Реализовать алгоритм выбора Select, и, опционально, External Sorting**

**9) Реализовать АВЛ дерево**
   
   Методы к реализации:
   
   insert, remove, rebalance
   
   Опционально 1:
   
   Декартово дерево, операции merge, split, add, remove
   
   Опционально 2:
   
   Написать реализацию бинарного дерева поиска или расширяющегося дерева или рандомизированного дерева
   
   Включить в глобальный тест
   
**10) Реализация красно-чёрного дерева, вставки и поиска**

   Основное задание:
    
   1. Реализовать самостоятельно красно-черное дерево, операции вставка, удаление, поиск
    
   2. Тест на производительность
    
   Сравнить АВЛ-дерево и красно-черное дерево
   
   - тест вставки
   - тест поиска
   - тест удаления
    
   Тест вставки
   - 5 млн случайных чисел - random(0..LONG_MAX)
   - 5 млн упорядоченных чисел
   - данные из dataset в материалах занятия. Код загрузки датасета в массив там же.
    
   Замерить высоту дерева
    
   Тест поиска
   - 5 млн случайных чисел - random(0..LONG_MAX)
   - 5 тыс случайных чисел, сохранить в массив, потом поиск этих чисел в цикле 1000 раз. Общее количество поиска - 5 млн
   - 5 млн случайных чисел из датасет
    
   Тест удаления
   - 1 млн случайных чисел
    
   Замерить высоту дерева
    
   Опционально:
   
   Реализовать на выбор одно из деревьев
   - BST - бинарное дерево поиска
   - Splay - расширяющееся дерево
   - рандомизированное дерево
    
**11) Реализовать оптимальное дерево поиска**

   Сравнить
    
   1) время построения, включая сортировку
   2) время поиска
    
**12) Реализовать хеш-таблицу, использующую метод цепочек**

   - дополнительно: для хранения внутри цепочек при достижении значительного числа элементов (~32) 
   заменять их на BST
    
   **Или: реализовать хеш-таблицу с открытой адресацией**
   - дополнительно: реализовать "ленивое" удаление
   - реализовать квадратичный пробинг
    
**13) Реализовать алгоритм Косарайю**
    
   Входные данные:
    
   Граф задан вектором смежности int A[N][Smax]. Это п.5 в структурах данных в лекции. Отличие только в том, что вершины нумеруются от 0 а не от 1, и номера самой вершины первым столбцом в матрице не будет, будут только номера смежных вершин
    
   Задание:
    
   Реализовать алгоритм Косарайю, рекурсивный вариант, как он был дан в лекции
   Если понадобится использование стека/очереди обязательно применение собственных структур данных из предыдущих занятий
    
   Выходные данные:
    
   Результат должен быть представлен в виде массива int[] component где элемент с номером вершины содержит номер компонента
    
   Дополнительное задание 1
    
   Реализовать итеративный поиск в глубину с сохранением состояния, что бы уже пройденные уровни повторно не проходились
    
   Дополнительное задание 2
    
   Реализовать поиск по критерию стоимости
    
**14) Реализовать алгоритм Демукрона** 
    
   Граф задан вектором смежности int A[N][Smax]. Это п.5 в структурах данных в лекции. Отличие только в том, что вершины нумеруются от 0 а не от 1, и номера самой вершины первым столбцом в матрице не будет, будут только номера смежных вершин
    
   Задание:
    
   Реализовать алгоритм Демукрона.
   Если понадобится использование стека/очереди обязательно применение собственных структур данных из предыдущих занятий
   Можно использовать стандартный массив [], встроенный в язык
    
   Выходные данные:
    
   Результат должен быть представлен в виде массива int[][] level где первый индекс - номер уровня, на каждом уровне массив, с номерами вершин, принадлежащих этому уровню
    
   Дополнительное задание 1
    
   Реализовать алгоритм Тарьяна
    
   Дополнительное задание 2
    
   Реализовать алгоритм поиска мостов или точек сочленения
   
**15) Реализовать алгоритм нахождения минимального остовного дерева**

   Реализовать алгоритм Краскала 
    
   Граф задан вектором смежности int A[N][Smax]. Это п.5 в структурах данных в лекции. Отличие только в том, что вершины нумеруются от 0 а не от 1, и номера самой вершины первым столбцом в матрице не будет, будут только номера смежных вершин
    
   Задание:
   
   Реализовать алгоритм Краскала
   
   Структура Union-Find собственной реализации.
   Если понадобится использование стека/очереди обязательно применение собственных структур данных из предыдущих занятий
   Можно использовать стандартный массив [] встроенный в язык
    
   Выходные данные:
   
   Результат должен быть представлен в виде массива Edge[] edges где Edge - класс, содержащий пару вершин, 
   которые соединяет это ребро
   
   Edge
   {
   int v1;
   int v2;
   }
   
   Для любителей компактного хранения можно упаковать в long два int-а.
   Тогда результат будет long[] edges
    
   Дополнительное задание 1
   
   Реализовать алгоритм Прима
   
   Дополнительное задание 2
   
   Реализовать алгоритм Борувки
   
**16) Реализовать алгоритм Дейкстры**

   Реализовать классику всех времен и народов, алгоритм Дейкстры
    
   Граф задан вектором смежности int A[N][Smax]. Это п.5 в структурах данных в лекции. Отличие только в том, что вершины нумеруются от 0 а не от 1, и номера самой вершины первым столбцом в матрице не будет, будут только номера смежных вершин
    
   Задание:
   
   Реализовать алгоритм Дейкстры.
   Если понадобится использование дерева/кучи обязательно применение собственных структур данных из предыдущих занятий.
   Можно использовать стандартный массив [], встроенный в язык.
    
   Выходные данные:
   
   Результат должен быть представлен в виде массива Edge[] edges, где Edge - класс, содержащий пару вершин, которые соединяет это ребро
   
   Edge
   {
   int v1;
   int v2;
   }
   
   Для любителей компактного хранения можно упаковать в long два int-а.
   Тогда результат будет long[] edges
    
   Дополнительное задание 1
   
   "Расскажи своей бабушке". 
   Рассказать идею алгоритма Дейкстры совей бабушке так, что бы она это поняла. Поделиться своим опытом в слаке. Не забыть приложить ссылку на пост в задании 
    
   Дополнительное задание 2
   
   Реализовать алгоритм Флойда-Уоршалла или Беллама-Форда на выбор

**17) Предложить вариант алгоритма для решения задачи**

   Задача: 
   
   Нужно быстро строить кратчайший маршрут на большие расстояния по реальной дорожной сети, например от Лиссабона до Владивостока. Можно взять данные OSM.
    
   Предложить вариант решения задачи, работающий быстрее, чем применение A* в лоб.
    
   Идею и описание алгоритма прислать в виде небольшой записки.
    
   Дополнительное задание 1: Закодить алгоритм A*
   
   Дополнительное задание 2: Закодить алгоритм Джонсона

**18) Алгоритм Бойера-Мура (отдельные части)**

   Реализовать функцию `FindPrefixes`, которая создает таблицу префиксов (примеры работы есть в материале):
   Функция `FindPrefixes(string)` возвращает массив `Z` такой, что `Z[i]` содержит число, равное длине подстроки `S[i]`, котороая при этом является и префиксом `S`. Если такого префикса нет, то `Z[i]` будет 0.

   Есть тест-кейсы: файл `preprocess_test_cases.txt`. 
   
   Традиционно, первая строка - количество записей, первый столбец - строка, далее через пробел - записанные последовательно значения из таблицы префиксов для данной строки. 
   
   Реализовать алгоритм Бойера-Мура-Хорспула. 
   
   Для проверки есть тест-кейсы: `string_matching_test_cases.tsv`. Это .tsv без заголовка, но в первой строчке общее число записей. Первый столбец - текст (содержит пробелы), после первого таба - столбец с паттерном (также может содержать пробелы), в последнем столбце через пробел _все_ вхождения паттерна в текст, в том числе, перекрывающиеся частично. Если вхождений нет, столбец пуст.
   
   (для тех, кто пишет на C): проверить работу алгоритма Бойера-Мура, 
   
   реализация на С, [из википедии](https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string-search_algorithm#Implementations). Можно на предложенных тест-кесах, можно на своих.

**19) Конечный автомат и алгоритм Кнтуа-Морриса-Пратта**

   Реализовать алгоритм вычисления функции переходов для паттерна, работающий за время O(m^3 * |Sigma|)
   
   Дополнительно:
   
   - Реализовать при помощи оптимизации по поиску префиксов, аналогичному алгоритму КМП, алгоритм для составления конечного автомата за O(m * |Sigma|)
  
   - Разобрать 2 примера работы алгоритма на разных строках (ориентироваться на строку длиной 5-7 символов), в которых при этом есть префиксы, одновременно являющиеся суффиксами
   
**20) Реализовать trie и вычисление префиксных ссылок**

   Основное задание:
   
   * Реализовать trie для хранения строк (тем, кто делал Radix Sort через trie, повезло!)
   
   * Написать алгоритм, который для одной строки строит trie и рассчитывает ссылки на префиксы
   
   Дополнительные задания:
   
   * Написать код для "сложения" (слияния) двух trie
   
   * Написать алгоритм, строящий дерево с префиксными ссылками для произвольно количества строк

**21) Алгоритм RLE**

   Создайте программу для сжатия и распаковки файлов по методу RLE.
   
   Проверьте работу программы на разных типах файлов: текст, фото, видео, исполняемый файл.
   
   Доработайте программу для улучшенного алгоритма RLE.
   
   Сравните результаты работы первой и второй версии программы.
   
**22) Кэширование**

   Написать свою реализацию скользящего timed-кэша на вашем языке программирования (C/C++/Java/Python).
   
   Более подробное описание см. в материалах вебинара.

**23)  Решить задачу про сарай фермера**

   Фермер хочет построить на своей земле как можно больший по площади сарай. 
   Но на его участке есть деревья и хозяйственные постройки, которые он не хочет никуда переносить. 
   Для удобства представим ферму сеткой размера N × M.
   Каждое из деревьев и построек размещается в одном или нескольких узлах сетки. 
   Найти максимально возможную площадь сарая и где он может размещаться.
   Начальные данные: Вводится матрица размера N × M из 0 и 1.
   1 соответствует постройке, 0 - пустой клетке. 

   Начальные данные:
   
   На первой строке вводится размер матрицы N M (через пробел) от 1 до 1000.
   На второй строке вводится количество построек T (от 0 до 10000).
   Далее на T строчках вводится координаты построек по два числа X Y, где 0 <= X < N; 0 <= Y < M.

   Вывод результата:
   
   1 число, соответствующее максимальной площади сарая (количество ячеек)
