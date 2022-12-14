> git clone https://github.com/googlecodelabs/android-compose-codelabs
# Jetpack Compose
Jetpack Compose是用于**构建原生Android界面的新工具包**。
Compose的优势：更少的代码；直观；加快应用开发；功能强大。

> $$app
- Composable函数与预览
- 布局
- 配置布局：通过修饰符，可更改组合项的大小、布局、外观，添加高级互动，如点击
- Material Design：系统自动适应深色背景
- 列表与状态
- 动画
> MainActivity01-06
- Compose所解决的问题：关注点分离（Composable函数）
- 声明式UI
- 组合 vs 继承
- 重组：任何Composable函数在任何时候都可以被重新调用

> $$layout
# Compose布局
## 目标
实现高性能；轻松自定义布局。
在Compose中，通过避免多次测量布局子级实现高性能。如果需要进行多次测试，Compose具有一个特殊的系统，即固有特性测量。
## 标准布局组件
Column, Row, Box
## 修饰符
借助修饰符，修饰或扩充可组合项。使用修饰符来执行以下操作：
- 更改可组合项的大小，布局，行为和外观
- 添加信息，如无障碍标签
- 处理用户输入
- 添加高级互动，如使元素可点击、可滚动、可拖放或可缩放
> PhotographerCard
## Slots API
槽位会在界面中留出空白区域
- Scaffold：提供槽位
> ScaffoldTest
## 列表
- 简单的Column或Row
- 大量列表项（或长度未知的列表），LazyColumn或lazyRow
> ListTest
## 自定义布局
layout修饰符修改元素的测量和布局方式。Layout是一个lambda；
它的参数包括**测量元素**（以measurable的形式传递）以及可组合项的传入**约束条件**（以constraints的形式传递）
> FirstBaselineToTop
> MyOwnColumn
> StaggeredGrid
## 约束布局ConstraintLayout
- createRefs(), createRefFor()创建引用
- 使用Modifier.constrainAs来提供约束，引用作为它的第一个参数，在主体lambda中指定其约束条件
- 使用linkTo指定约束
- parent是一个现有引用
- 解耦API：约束条件和布局分离。例如根据屏幕配置来更改约束条件，或在两个约束条件集之间添加动画效果。
  1）将ConstraintSet作为参数传递给ConstraintLayout
  2）使用layoutId修饰符将ConstraintSet中创建的引用分配给可组合项
> ConstraintLayout
## Intrinsics
Compose只测量子元素一次，测量两次会引发运行时异常。但有时在测量子元素之前，需要有关子元素信息。
Intrinsics允许在实际测量前查询子项。
(min|max)IntrinsicWidth, (min|max)IntrinsicHeight
> TwoTexts

> $$state
# Compose状态
应用中的状态指可以变化的任何值
## 非结构化状态
当添加更多事件和状态时，可能出现问题：
- 测试，由于UI与View代码交织，很难测试此代码。
- 部分状态更新
- 部分UI更新
- 代码复杂性 
> HelloComposeStateActivity
## 单向数据流
为了解决非结构状态的这些问题，引入ViewModel和LiveData。
将状态从Activity移到了viewModel，在ViewModel中，状态由LiveData表示。LiveData是一种可观察状态容器。在界面使用observe方法，以便状态变化时更新界面。

单向数据流是一种状态向下流动而事件向上流动的设计，优势有：
- **可测试性**，通过状态与UI分离，更轻松的测试Activity和ViewModel。
- **状态封装**，因为状态只能在一个地方（ViewModel）更新，随UI的增长，不太可能引入部分状态更新错误。
- **UI一致性**，所有状态更新都通过使用可观察状态持有者立即反映在UI中。
> HelloComposeStateActivityWithViewModel
## 状态提升
一种将状态移至可组合项的调用方，以使可组合项无状态的模式。无状态组件更容易测试，更少的错误，并提供更多重用机会。

1. 当应用于可组合项时，通常意味着向可组合项引入两个参数。
- **value T** 要显示的当前值  
- **onValueChange: (T)->Unit** 请求更改值的事件

2. 使用ViewModel来提升TodoScreen中的状态。创建单向数据流设计：ViewModel-state->TodoScreen-event->ViewModel
> one.TodoScreen
## 重组与remember
Compose框架可以智能地仅重组已更改的组件。
remember提供了可组合函数内存。系统会将由remember计算的值存储在组合树中，只有当remember的键发生变化时才会重新计算该值。
可以将remember看作为函数提供单个对象的存储空间，过程与private val属性在对象中执行的操作相同。
> one.TodoScreen.TodoRow
## 有状态与无状态
使用remember存储对象的可组合项，会创建内部状态，使该组合项有状态。
具有内部状态的可组合项往往**不易重复使用，也更难测试**。
无状态可组合项指不保存任何状态的可组合项。实现无状态的一种简单方法是**状态提升**。
## MutableState
1. `val (text, setText) = remember {mutableStateOf("")}`
这个函数使用remember给自己添加内存，然后在内存中存储一个由mutableStateOf创建的MutableState<String>,
它是Compose的内置类型，提供一个可观察的状态持有者。
val (value, setValue) = remember {mutableStateOf(default)}
对value的任何更改都将自动重新组合读取此状态的任何可组合函数。

2. 通过以下MutableState三种方式声明一个可组合对象：
- val state = remember {mutableStateOf(default)}
- val value by remember {mutableStateOf(default)}
- val (value, setValue) = remember {mutableStateOf(default)}

3. 在组合中创建State<T>时，**务必对其执行remember操作**，否则它会在每次重组时**重新初始化**。
MutableState<T>类似MutableLiveData<T>，但与Compose运行时集成。由于它是可观察的，它会在更新时通知Compose。
> one.TodoComponents.TodoItemInput
## 软键盘
- keyboardOptions，用于启动显示完成IME操作。
- keyboardActions，用于指定响应触发的特定IME操作而触发的操作。
> two.TodoComponents.TodoItemInput
## 管理状态
**ViewModel作为状态容器**
> four.TodoVewModel
## 恢复状态
1. rememberSaveable恢复界面状态。
2. 添加到Bundle的所有数据类型会自动保存。要保存无法添加到Bundle的内容，选择Parcelize, MapSaver, ListSaver。
> StateRecoveryParcelableActivity
> StateRecoveryMapSaverActivity
> StateRecoveryListSaverActivity

> $$local
# CompositionLocal
## 比较
- 显示传参：劣势：繁琐；优势：数据隔离
- 隐式传参：优势：方便；劣势：一改全改
## CompositionLocal
CompositionLocal，创建**以树为作用域的具名对象**，，用作让数据流经界面树的一种**隐式方式**。
1. MaterialTheme的color、shapes和typography属性访问LocalColor、LocalShapes和LocalTypography属性。
> CompositionLocalSample1
2. 为CompositionLocal提供新值，使用CompositionLocalProvider及其**provides infix**函数
3. CompositionLocal的**current**值对应该组合部分中某个祖先提供的最接近的值
> CompositionLocalSample2
## 创建CompositionLocal
> MyCard CompositionLocalSample3
- compositionLocalOf，如果更改提供的值，会使读取其current值的组件发生重组。
- staticCompositionLocalOf，如果更改值，会使提供CompositionLocal的**整个content lambda被重组**。
  如果为CompositionLocal提供的值更改可能性很小或不会更改，使用staticCompositionLocalOf可提高性能。
> CompositionLocalSample4

> $$theming
# Compose主题
## Material Design
Material Design是一个用于创建**数字界面的综合设计体系**。Material Design组件（按钮、卡片、开关等）建立在Material Theme之上，Material Theme包括颜色、排版和形状属性。
## 定义主题
- 创建主题
- 颜色
- 排版
- 形状
- 深色主题
> JetnewsTheme
## 使用颜色
- 原色：Color(0xffff00ff)
- 主题颜色：`MaterialTheme.colors.onSurface.copy(alpha=0.1f)`
- 许多组件接收一对颜色：颜色和内容颜色
- 内容Alpha值： LocalContentAlpha `CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium)`
- primarySurface：浅色primary，深色surface(许多组件默认使用此策略，如用栏和底部导航栏)
## 处理文本
- 使用ProvideTextStyle可组合项来设置当前TextStyle
- 主题样式：MaterialTheme.typography.subtitle2
- 多种样式：AnnotationString
- overline文本样式: `MaterialTheme.typography.overline.toSpanStyle().copy(background=MaterialTheme.colors.primary.copy(alpha = 0.1f))`
## 处理形状
MaterialTheme.shapes.small

> $$AnimationCodelab
# 动画
- 简单值动画：animateColorAsState, animateSizeAsState
- 可见性动画: AnimatedVisibility
- 内容大小动画: animateContentSize
- 多值动画：
  updateTransition设置一个Transition，并使用targetState提供的目标对其进行更新。
  当targetState更改时，Transition将朝着为新targetState指定的目标值运行所有子动画。
  使用Transition动态添加子动画：Transition.animateFloat、animateColor、animateValue等
  弹性：spring
- 重复动画：rememberInfiniteTransition, animateFloat, repeatMode
- 手势动画: awaitPointEventScope

> $$gesture
# 手势
## 点击
- clickable 
- pointInput修饰符使用detectTapGestures
> ClickableSample 
## 滚动
- 滚动修饰符：verticalScroll, horizontalScroll
- 可滚动修饰符：scrollable，与滚动修饰符不同，区别在于scrollable可检测滚动手势，但不会偏移其内容。必须提供consumeScrollDelta函数
- 自动嵌套滚动
> ScrollSample ScrollSample2
> ScrollableSample
> NestedScrollSample
## 拖动
- draggable，与scrollable类似，仅检测手势。需要保存状态并在屏幕上表示，如通过offset修饰符移动元素。
- pointerInput修饰符使用detectDragGestures
> DraggableSample
> DragWhereYouWantSample
## 滑动
swipeable, 常见用途是实现"滑动关闭"模式
> SwipeableSample
## 多点触控：平移、缩放、旋转
transformable, 修饰符本身不会转换元素，只会检测手势
> TransformableSample

> $$MigrationCodelab
# Compose集成
- ComposeView：使用其setContent方法托管Compose UI内容
- AndroidView: Compose暂不支持Spanned，也不显示HTML文本，需要在Compose中使用TextView绕过此限制
- MdcTheme: 共用主题。Compose继承View系统中的可用的主题

> $$NavigationCodelab
# Compose导航
- 集成导航
- 参数传递
- 深层链接
> RallyAppWithNavigation

> $$effects
# Compose 附带效应
1. 纯函数：指函数与外界交换数据只通过函数参数和函数返回值来进行的，纯函数的运行不会对外界环境产生任何的影响。
2. 附带效应：产生运算以外的其他结果。最典型修改了外部环境的变量值。
3. 组合函数的特点：
- 执行顺序不定
- 可以并行运行
- 可能会非常频繁地运行
4. 处理副作用：
- 执行时机是明确的
- 执行次数是可控的
- 不会造成泄漏
5. 组合函数的副作用：在可组合函数内部**理应只做视图相关的事情**，而不应该做函数返回之外的事情，如访问文件等。
危险的附带效应：
- 写入共享对象的属性
- 更新ViewModel中的可观察项
- 更新共享偏好设置
6. 组合函数的生命周期：
- Enter: 挂载到树上，首次显示
- Composition: 重组刷新UI
- Leave: 从树上移除，不再显示
组合函数中没有自带的生命周期函数，要监听其生命周期，使用Effect API:
- LaunchedEffect: 第一次调用Compose函数时调用
- DisposableEffect: 内部有一个onDispose函数，当页面退出时调用
- SideEffect: Compose函数每次执行都会调用
## LaunchedEffect
LaunchedEffect用于创建协程。
- 当LaunchedEffect进入组件树，会启动一个协程，把将block放入该协程执行。
- 当组合函数从树上detach时，协程还未被执行完毕，该协程**也会被取消执行**。
- 当LaunchedEffect在重组时key不变，那LaunchedEffect不会被重新启动执行block.
- 当LaunchedEffect在重组时key变了，则LaunchedEffect会执行cancel后，再**重新启动一个新协程执行block**.
> LaunchedEffectSample
## rememberCoroutineScope
- 由于LaunchedEffect是可组合函数，只能在其他可组合函数中使用。想要**在可组合项外启动协程**，
且需要对这个协程存在作用域限制，以便**协程在退出组合后自动取消**，可以使用rememberCoroutineScope。
- 需要**手动控制一个或多个协程的生命周期**，使用rememberCoroutineScope。
> RememberCoroutineScopeSample
## rememberUpdatedState
给某个参数创建一个引用，来跟踪这些参数，**并保证其值被使用时是最新值，参数被改变时不重启effect**
> RememberUpdatedStateSample
## DisposedEffect
DisposedEffect也是可组合函数，当DisposedEffect在其key值变化或组合函数离开组件树时，会取消之前启动的协程，
**并在取消协程前调用其回收方法进行资源回收相关的操作**。
> DisposedEffectSample
## SideEffect
- SideEffect是简化版的DisposedEffect，SideEffect并未接收任何key值，所以每次重组就会执行其block。当不需要onDispose，不需要参数控制时使用SideEffect。
  SideEffect主要用来**与非Compose管理的对象共享Compose状态**。
- SideEffect在组合函数被创建并**载入视图数后才会被调用**
## produceState
将非Compose(如Flow、LiveData或RxJava)状态转换为Compose状态。接收一个lambda表达式作为函数体，能将这些入参经过一些操作后
**生成一个State类型变量并返回**。
- produceState创建了一个**协程**，也可用于**观察非挂起的数据源**。
- 当produceState进入Composition，获取数据的任务被启动，当离开Composition时，该任务被取消。
> ProduceStateSample
## derivedStateOf
从其他状态对象计算或派生。当任意条件状态更新时，结果状态都会重新计算
> DerivedStateOfSample
## snapshotFlow
将State对象转换为Flow。
当snapshotFlow块中的读取的State对象值发生变化时，如果新值与之前发出的值不相等， Flow会向其收集器发出新值。
> SnapshotFlowSample