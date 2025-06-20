<script>
	let { children } = $props();
</script>

<style>

</style>

<header>
    <h1>Header du site</h1>
    <nav>
        <a href="/">Home</a>
        <a href="/specie">Species</a>
        <a href="/observation">Observation</a>
        <a href="/travellogs">Travel Logs</a>
    </nav>
</header>

{@render children()}